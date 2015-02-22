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
DEFAULT_GUESTBOOK_NAME = 'default_guestbook'


def guestbook_key(guestbook_name=DEFAULT_GUESTBOOK_NAME):
	"""Constructs a Datastore key for a Guestbook entity.
	"""
	return ndb.Key('Guestbook', guestbook_name)



url = START_URL
json_response = urllib2.urlopen(url)
data = json.load(json_response)


class Listing(ndb.Model):
	"""Models one listing/posting on facebook """
	title = ndb.StringProperty(indexed=False)
	message = ndb.StringProperty(indexed=False)
	category = ndb.StringProperty(indexed=True)

for item in data['data']:
    #print "New item starts here"
    message = item['message']
    title = " ".join(message.split()[:10]) + " ..."
    new_listing = Listing(parent=guestbook_key(DEFAULT_GUESTBOOK_NAME))
    new_listing.title = title
    new_listing.message = message
    new_listing.category = "All"
    new_listing.put()




class MainHandler(webapp2.RequestHandler):
    def get(self):

    	greetings_query = Listing.query(ancestor=guestbook_key(DEFAULT_GUESTBOOK_NAME))
        greetings = greetings_query.fetch(10)

        for greeting in greetings:
			self.response.write(greeting.message)

app = webapp2.WSGIApplication([
    ('/', MainHandler)
], debug=True)
