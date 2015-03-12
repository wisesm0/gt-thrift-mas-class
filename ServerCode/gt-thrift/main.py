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
from google.appengine.ext import ndb

START_URL = "https://graph.facebook.com/v2.2/199456403537988/feed?access_token=CAANI8ZBCAeOwBAHy59OJRb3B9y9X9aNn2XekTCjBsEWlR4H71M0srnqT4DJ6fnZBvPfilH8Nm4xXBYAZA3JwMqD5ElrlcptoNaEDZCRYtRTaPqhRqio7S6ioxTYXOT2ABANENj2mrHF6FNBv6et3X7hrbMzsIU8ZBK3YN5LlcNzAoQFpBB3HtNw354QdVggfLAemF4fzElS3nVRB6yOqE"
DEFAULT_GUESTBOOK_NAME = 'default_guest'
per_page = 20

def guestbook_key(guestbook_name=DEFAULT_GUESTBOOK_NAME):
    """Constructs a Datastore key for a Guestbook entity.
    """
    return ndb.Key('Guestbook', guestbook_name)

class Listing(ndb.Model):
    """Models one listing/posting on facebook """
    title = ndb.StringProperty(indexed=True)
    message = ndb.StringProperty(indexed=True)
    category = ndb.StringProperty(indexed=True)
    date = ndb.DateTimeProperty(auto_now_add=True)
    picture = ndb.StringProperty(indexed=False)
    post_id = ndb.StringProperty(indexed=True)
    author_name = ndb.StringProperty(indexed=False)
    author_id = ndb.StringProperty(indexed=True)
    link_to_post = ndb.StringProperty(indexed=False)

class MainHandler(webapp2.RequestHandler):
    def get(self):
        url = START_URL
        json_response = urllib2.urlopen(url)
        data = json.load(json_response)
        listings_list = []
        all_listings = Listing.query()
        cnt = 0
        # for each in all_listings:
        #     cnt += 1
        # self.response.write("query count: " + str(all_listings.count()))
        # self.response.write(str(all_listings))
        # self.response.write(data['data'][0])
        for item in data['data']:
            new_listing = Listing(parent=guestbook_key(DEFAULT_GUESTBOOK_NAME))
            d = {}
            if 'message' in item:
                messagez = item['message']
            else:
                messagez = ""
            title = " ".join(messagez.split()[:10]) + " ..."
            d['message'] = messagez
            d['title'] = title

            if 'picture' in item:
                d['picture'] = item['picture']

            res = Listing.query(Listing.post_id == item['id'])
            if res.count() > 0:
                cnt += 1
            # new_listing.title = d['title']
            # new_listing.message = d['message']
            # new_listing.post_id = item['id']
            # new_listing.author_id = item['from']['id']
            # new_listing.author_name = item['from']['name']
            # new_listing.category = "all"
            # new_listing.put()
            listings_list.append(d)
        js = json.dumps(listings_list)
        self.response.write(str(cnt))
        

class PageHandler(webapp2.RequestHandler):
    def get(self):
        self.response.headers['Content-Type'] = 'text/plain'
        self.response.write("hiiiii")

class DeleteAllHandler(webapp2.RequestHandler):
    def get(self):
        ndb.delete_multi(Listing.query().fetch(keys_only=True))
        self.response.write("Deleted all keys")

class PageCountHandler(webapp2.RequestHandler):
    def get(self):
        self.response.write(str(Listing.query().count()))
        
class updatedbHandler(webapp2.RequestHandler):
    def get(self):
        url = START_URL
        json_response = urllib2.urlopen(url)
        data = json.load(json_response)
        cnt = 0
        for item in data['data']:
            res = Listing.query(Listing.post_id == item['id'])
            if res.count() == 0:
                d = {}
                if 'message' in item:
                    messagez = item['message']
                else:
                    messagez = ""
                title = " ".join(messagez.split()[:10]) + " ..."
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
                new_listing.author_id = item['from']['id']
                new_listing.author_name = item['from']['name']
                new_listing.category = "all"
                new_listing.picture = d['picture']
                if 'link' in item:
                    new_listing.link_to_post = item['link']
                new_listing.put()
                cnt += 1
        self.response.write("Added Items: " + str(cnt))

class UserHandler(webapp2.RequestHandler):
    def get(self, username):
        self.response.write("Requested records for user: " + str(username))

class getPostsHandler(webapp2.RequestHandler):
    def get(self):
        result = Listing.query()
        # self.response.write(result.count())
        listings_list = []
        page_no = 1
        cnt = 0
        for listing in result:
            cnt = cnt + 1
            if cnt > (per_page * page_no - 1) and cnt < (per_page * page_no):
                continue
            d = {}
            d['message'] = listing.message
            d['title'] = listing.title
            d['post_id'] = listing.post_id
            d['author_id'] = listing.author_id
            d['author_name'] = listing.author_name
            d['category'] = listing.category
            d['picture'] = listing.picture
            d['link_to_post'] = listing.link_to_post
            listings_list.append(d)
        js = json.dumps(listings_list)
        self.response.write(js)

class getSecondPageHander(webapp2.RequestHandler):
    def get(self):
        result = Listing.query()
        # self.response.write(result.count())
        listings_list = []
        page_no = 2
        cnt = 0
        for listing in result:
            d = {}
            cnt = cnt + 1
            if cnt > (per_page * page_no - 1) and cnt < (per_page * page_no):
                continue
            d['message'] = listing.message
            d['title'] = listing.title
            d['post_id'] = listing.post_id
            d['author_id'] = listing.author_id
            d['author_name'] = listing.author_name
            d['category'] = listing.category
            d['picture'] = listing.picture
            d['link_to_post'] = listing.link_to_post
            listings_list.append(d)
        js = json.dumps(listings_list)
        self.response.write(js)

app = webapp2.WSGIApplication([
    ('/getfirstpage/?',getPostsHandler),
    ('/getsecondpage/?',getSecondPageHander),
    ('/getuser/<username:\s+>',UserHandler),
    ('/getpagecount/?',PageCountHandler),
    ('/deleteall/?',DeleteAllHandler),
    ('/page/?',PageHandler),
    ('/updatedb/?',updatedbHandler),
    ('/', MainHandler)
], debug=True)
