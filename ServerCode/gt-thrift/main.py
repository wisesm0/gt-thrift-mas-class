#!/usr/bin/env python
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
import webapp2
import urllib2
import json
import logging
import cgi
import hashlib

from google.appengine.ext import ndb

from constants import CATEGORIES, PER_PAGE, CATEGORY_SMALL_TO_BIG_MAP

START_URL = "https://graph.facebook.com/v2.2/199456403537988/feed?access_token=CAANI8ZBCAeOwBAHy59OJRb3B9y9X9aNn2XekTCjBsEWlR4H71M0srnqT4DJ6fnZBvPfilH8Nm4xXBYAZA3JwMqD5ElrlcptoNaEDZCRYtRTaPqhRqio7S6ioxTYXOT2ABANENj2mrHF6FNBv6et3X7hrbMzsIU8ZBK3YN5LlcNzAoQFpBB3HtNw354QdVggfLAemF4fzElS3nVRB6yOqE"
DEFAULT_GUESTBOOK_NAME = 'default_guest'


def categorize(description):
    if description:
        words = description.split()
        for category_name, keyword_dict in CATEGORIES:
            for word in words:
                if word.lower() in keyword_dict:
                    return category_name
    return "Others"

def guestbook_key(guestbook_name=DEFAULT_GUESTBOOK_NAME):
    """Constructs a Datastore key for a Guestbook entity.
    """
    return ndb.Key('Guestbook', guestbook_name)

class Listing(ndb.Model):
    """Models one listing/posting on facebook """
    title = ndb.StringProperty(indexed=True)
    message = ndb.StringProperty(indexed=True)
    category = ndb.StringProperty(indexed=True)
    date = ndb.StringProperty(indexed=True)
    picture = ndb.StringProperty(indexed=False)
    post_id = ndb.StringProperty(indexed=True)
    author_name = ndb.StringProperty(indexed=False)
    author_id = ndb.StringProperty(indexed=True)
    link_to_post = ndb.StringProperty(indexed=False)


class DeleteAllHandler(webapp2.RequestHandler):
    def get(self):
        ndb.delete_multi(Listing.query().fetch(keys_only=True))
        self.response.write("Deleted all keys")
        
class updatedbHandler(webapp2.RequestHandler):
    """
    This function take all the history from Facebook group and update it in our database
    We need not run this 
    """
    def get(self):
        url = START_URL
        cnt = 0
        # debug_string = ""
        for page_no in range(20):
            json_response = urllib2.urlopen(url)
            data = json.load(json_response)
            # debug_string += "Page no fetching is " + str(page_no) + "\n"
            for item in data['data']:
                res = Listing.query(Listing.post_id == item['id'])
                if res.count() == 0:
                    d = {}
                    if 'message' in item:
                        messagez = item['message']
                    else:
                        messagez = ""
                    title = " ".join(messagez.split()[:5]) + " ..."
                    d['message'] = messagez
                    d['title'] = title

                    if 'picture' in item:
                        d['picture'] = item['picture']
                    else:
                        d['picture'] = ""
                    new_listing = Listing(parent=guestbook_key(DEFAULT_GUESTBOOK_NAME))
                    new_listing.title = d['title']
                    new_listing.message = d['message'][0:500]
                    new_listing.post_id = item['id']
                    new_listing.date = item['created_time']
                    new_listing.author_id = item['from']['id']
                    new_listing.author_name = item['from']['name']
                    # debug_string += "Messages : " + d['message'] + "\n"
                    # debug_string += "Category : " + categorize(d['message']) + "\n"
                    new_listing.category = categorize(d['message'])
                    new_listing.picture = d['picture']
                    if 'link' in item:
                        new_listing.link_to_post = item['link']
                    else:
                        new_listing.link_to_post = item['actions'][0]['link']
                    new_listing.put()
                    cnt += 1
            url = data['paging']['next']
        self.response.write("Updated the database with " + str(cnt) + " new entries")


class CategoryHandler(webapp2.RequestHandler):
    def get(self, cat, page_no):
        category = str(cat)
        page_no = int(page_no)
        category_big = CATEGORY_SMALL_TO_BIG_MAP[category]
        logging.debug("Category received is " + category_big)
        result = Listing.query(Listing.category == category_big).order(-Listing.date);
        listings_list = []
        cnt = 0
        cont_count = 0
        # debug_string = []
        for listing in result:
            cnt = cnt + 1
            if cnt > (PER_PAGE * (page_no - 1)) and cnt <= (PER_PAGE * page_no):
                d = {}
                d['message'] = listing.message
                d['title'] = listing.title
                d['post_id'] = listing.post_id
                d['author_id'] = listing.author_id
                d['author_name'] = listing.author_name
                d['category'] = listing.category
                d['picture'] = listing.picture
                d['date'] = str(listing.date)
                d['link_to_post'] = listing.link_to_post
                listings_list.append(d)
        js = json.dumps(listings_list)
        self.response.write(js)


class UserHandler(webapp2.RequestHandler):
    def get(self, author_id, page_no):
        author_id = str(author_id)
        page_no = int(page_no)
        result = Listing.query(Listing.author_id == author_id).order(-Listing.date);
        listings_list = []
        cnt = 0
        cont_count = 0
        # debug_string = []
        for listing in result:
            cnt = cnt + 1
            if cnt > (PER_PAGE * (page_no - 1)) and cnt <= (PER_PAGE * page_no):
                d = {}
                d['message'] = listing.message
                d['title'] = listing.title
                d['post_id'] = listing.post_id
                d['author_id'] = listing.author_id
                d['author_name'] = listing.author_name
                d['category'] = listing.category
                d['picture'] = listing.picture
                d['date'] = str(listing.date)
                d['link_to_post'] = listing.link_to_post
                listings_list.append(d)
        js = json.dumps(listings_list)
        self.response.write(js)


class PageHandler(webapp2.RequestHandler):
    def get(self, page_no):
        page_no = int(page_no)
        logging.debug("Page no received is " + str(page_no))
        result = Listing.query().order(-Listing.date);
        listings_list = []
        cnt = 0
        cont_count = 0
        # debug_string = []
        for listing in result:
            cnt = cnt + 1
            if cnt > (PER_PAGE * (page_no - 1)) and cnt <= (PER_PAGE * page_no):
                d = {}
                d['message'] = listing.message
                d['title'] = listing.title
                d['post_id'] = listing.post_id
                d['author_id'] = listing.author_id
                d['author_name'] = listing.author_name
                d['category'] = listing.category
                d['picture'] = listing.picture
                d['date'] = str(listing.date)
                d['link_to_post'] = listing.link_to_post
                listings_list.append(d)
        js = json.dumps(listings_list)
        self.response.write(js)

class PostListingHandler(webapp2.RequestHandler):
    def post(self):
        content = cgi.escape(self.request.get('content'))
        new_listing = Listing(parent=guestbook_key(DEFAULT_GUESTBOOK_NAME))
        new_listing.title = cgi.escape(self.request.get('title'))
        new_listing.message = cgi.escape(self.request.get('message'))[0:500]
        hash_object = hashlib.md5(new_listing.message)
        new_listing.post_id = hash_object.hexdigest()
        # new_listing.date = item['created_time']
        author_id = cgi.escape(self.request.get('author_id'))
        if author_id=="":
            author_id = "10200204025"   #default user
        new_listing.author_id = author_id
        author_name = cgi.escape(self.request.get('author_name'))
        if author_name=="":
            author_name = "Taylor Thrift"
        new_listing.author_name = author_name
        new_listing.category = cgi.escape(self.request.get('category'))
        new_listing.put()
        self.response.out.write('<html><body>You wrote:<pre>')
        self.response.out.write("post created")
        self.response.out.write('</pre></body></html>')

class AllPageHandler(webapp2.RequestHandler):
    def get(self):
        page_no = 1
        page_no = int(page_no)
        logging.debug("Page no received is " + str(page_no))
        result = Listing.query().order(-Listing.date);
        listings_list = []
        cnt = 0
        cont_count = 0
        # debug_string = []
        for listing in result:
            cnt = cnt + 1
            if cnt > (300 * (page_no - 1)) and cnt <= (300 * page_no):
                d = {}
                d['message'] = listing.message
                d['title'] = listing.title
                d['post_id'] = listing.post_id
                d['author_id'] = listing.author_id
                d['author_name'] = listing.author_name
                d['category'] = listing.category
                d['picture'] = listing.picture
                d['date'] = str(listing.date)
                d['link_to_post'] = listing.link_to_post
                listings_list.append(d)
        js = json.dumps(listings_list)
        self.response.write(js)

class ItemHandler(webapp2.RequestHandler):
    def get(self,itemid):
        # self.response.write(itemid)
        res = Listing.query(Listing.post_id == itemid)
        listings_list = []
        if res.count() > 0:
            # self.response.write(res)
            for item in res:
                d = {}
                d['message'] = item.message
                d['title'] = item.title
                d['post_id'] = item.post_id
                d['author_id'] = item.author_id
                d['author_name'] = item.author_name
                d['category'] = item.category
                d['picture'] = item.picture
                d['date'] = str(item.date)
                d['link_to_post'] = item.link_to_post
                listings_list.append(d)
        js = json.dumps(listings_list)
        self.response.write(js) 

app = webapp2.WSGIApplication([
    (r'/getlistings/(\d+)', PageHandler),
    (r'/getlistings/user/(.*)/(\d+)', UserHandler),
    (r'/getlistings/(.*)/(\d+)', CategoryHandler),
    (r'/getitem/(.*)',ItemHandler),
    ('/getalllistings/?',AllPageHandler),
    ('/deleteall/?',DeleteAllHandler),
    ('/updatedb/?',updatedbHandler),
    ('/postlisting/?',PostListingHandler)
], debug=True)
