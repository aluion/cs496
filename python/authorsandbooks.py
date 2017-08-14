
from google.appengine.ext import ndb
import webapp2
import json
class Author(ndb.Model):
    author_id = ndb.IntegerProperty(required=True)
    author_name = ndb.StringProperty(required=True)
    author_genres = ndb.StringProperty(repeated=True)
    author_bday = ndb.DateProperty(auto_now=True)
    author_works = ndb.StringProperty(repeated=True)

class Book(ndb.Model):
    book_id = ndb.IntegerProperty(required = True)
    book_author = ndb.StringProperty(repeated=True, required = True)
    book_title = ndb.StringProperty(required = True)
    book_genre = ndb.StringProperty(repeated=True)  
    book_pdate = ndb.DateProperty(auto_now=True)


class authorHandler(webapp2.RequestHandler):
   
    def post(self):
        post_data = json.loads(self.request.body)
        authorNumber = self.authorIncrement()
        new_author = Author(author_id=authorNumber, author_name=post_data["name"])
        new_author.author_genres = post_data['genre']
        new_author.author_works = post_data['works']
        new_author.put()
        author_dict = new_author.to_dict()
        author_dict['self'] = '/author/' + new_ship.key.urlsafe()
        self.response.write(json.dumps(author_dict))

    def get(self, id = None):
        if id: 
            author = ndb.Key(urlsafe=id).get()
            author_dict = author.to_dict()
            author_dict["self"] = '/ship/' + id
            self.response.write(json.dumps(author_dict))
        else:
            authors = Author.query().fetch()
            self.response.write(authors)

    def authorIncrement(self):
        authors = Author.query().fetch()#[1](see citations)
        author_num =  len(authors)
        return int(author_num) + 1

class slipHandler(webapp2.RequestHandler):
    def post(self):
        slip_data =  json.loads(self.request.body)
        number_of_slips = self.slipIncrement()
        new_slip = Slip(slip_id=str(number_of_slips), slip_number=slip_data["number"], current_boat="None" , arrival_date ="None")

        new_slip.put()
        slip_dict = new_slip.to_dict()
        slip_dict['self'] =  '/slip/' +  new_slip.key.urlsafe()
        self.response.write(json.dumps(slip_dict))
        
    def get(self, id = None):
        if id: 
            slip = ndb.Key(urlsafe=id).get()
            slip_dict = slip.to_dict()
            slip_dict["self"] = '/slip/' + id 
            self.response.write(json.dumps(slip_dict))
        else:
            
            slips = Slip.query().fetch() #[1](see citations)
            self.response.write(slips)

    def delete(self, id):
        if id:
            slip = ndb.Key(urlsafe = id),get()
            if(slip.current_boat != 'None')
            ndb.Key('Slip', int(id).delete())
            console.log('deleteing slip: ' + str(id))

            
            
        


    def slipIncrement(self):
        slips = Slip.query().fetch()
        s_num = len(slips)
        return  int(s_num) + 1        


# [START main_page]
class MainPage(webapp2.RequestHandler):

    def get(self):
       self.response.write("hello")

    
allowed_methods = webapp2.WSGIApplication.allowed_methods
new_allowed_methods = allowed_methods.union(('PATCH',))
webapp2.WSGIApplication.allowed_methods = new_allowed_methods

       
# [START app]
app = webapp2.WSGIApplication([
    ('/', MainPage),
    ('/ship', shipHandler),
    ('/ship/(.*)', shipHandler),
    ('/slip', slipHandler),
    ('/slip/(.*)', slipHandler)
], debug=True)
# [END app]


#citations
#[1] : https://stackoverflow.com/questions/36133912/python-gae-ndb-get-all-keys-in-a-kind