PER_PAGE = 30
KEYWORDS_ELECTRONICS_AND_COMPUTERS = {
    "lenovo" : True,
    "laptop" : True,
    "laptos" : True,
    "windows" : True,
    "gb" : True,
    "tb" : True,
    "mb" : True,
    "ram" : True,
    "hdd" : True,
    "ghz" : True,
    "pc" : True,
    "samsung" : True,
    "tv" : True,
    "microsoft" : True,
    "speakers" : True,
    "speaker" : True,
    "logitech" : True,
    "iphone" : True,
    "htc" : True,
    "smartphone" : True,
    "smartphones" : True,
    "android" : True,
    "keyboard" : True,
    "keyboards" : True,
    "vga" : True,
    "phone" : True,
    "phones" : True,
    "pentium" : True,
    "sata" : True,
    "mouse" : True,
    "usb" : True,
    "dell" : True,
    "headset" : True,
    "headsets" : True,
    "xbox" : True,
    "kinect" : True,
    "wifi" : True,
    "hdmi" : True,
    "macbook" : True,
    "macbooks" : True,
    "acer" : True,
    "ipod" : True,
}

KEYWORDS_HOME_GARDEN_TOOLS_AND_INSTRUMENTS = {
    "ikea" : True,
    "bookshelf" : True,
    "books" : True,
    "book" : True,
    "toaster" : True,
    "toasters" : True,
    "blenders" : True,
    "blender" : True,
    "dishes" : True,
    "utensils" : True,
    "nightstand" : True,
    "nightstands" : True,
    "chair" : True,
    "chairs" : True,
    "drawer" : True,
    "drawers" : True,
    "table" : True,
    "tables" : True,
    "cooker" : True,
    "cookers" : True,
    "futon" : True,
    "futons" : True,
    "rug" : True,
    "rugs" : True,
    "microwave" : True,
    "washer" : True,
    "dryer" : True,
    "desk" : True,
    "desks" : True,
    "cupboard" : True,
    "cupboards" : True,
    "mattress" : True,
    "matresses" : True,

} 
KEYWORDS_CLOTHING_SHOES_AND_JEWELERY = {
    "shoe" : True,
    "shoes" : True,
    "dress" : True,
    "dresses" : True,
    "slipper" : True,
    "slippers" : True,
}


KEYWORDS_TICKETS = {
    "ticket" : True,
    "tickets" : True,
    "concert" : True, 
} 

KEYWORDS_SPORTS_AND_OUTDOORS = {
    "callaway" : True,
}

KEYWORDS_MOTORCYCLES_SCOOTERS_AND_CARS = {
    "car" : True,
    "cars" : True,
    "bike" : True,
    "bikes" : True,
    "bicycle" : True,
    "bicycles" : True,
}

KEYWORDS_HOUSING = {
    "room" : True,
    "rooms" : True,
    "sublease" : True,
    "subleasing" : True,
    "house" : True,
    "houses" : True,
    "rent" : True,
    "sub-lease" : True,
    "apartment" : True,
    "apartments" : True,
    "subletting" : True,
    "sub-letting" : True,
    "sublet" : True,
    "sub-let" : True,
    "bedroom" : True,
    "bedrooms" : True,
    "bathroom" : True,
    "bathrooms" : True,
    "roommates" : True,
    "roommate" : True

}


CATEGORIES = [ ("Housing", KEYWORDS_HOUSING), ("Electronics and Computers", KEYWORDS_ELECTRONICS_AND_COMPUTERS), ("Home, Garden and Tools", KEYWORDS_HOME_GARDEN_TOOLS_AND_INSTRUMENTS), \
               ("Clothing, Shoes and Jewelery", KEYWORDS_CLOTHING_SHOES_AND_JEWELERY), ("Tickets", KEYWORDS_TICKETS), ("Sports and Outdoors", KEYWORDS_SPORTS_AND_OUTDOORS), \
               ("Motorcycles, Scooters and Cars", KEYWORDS_MOTORCYCLES_SCOOTERS_AND_CARS)]


CATEGORY_SMALL_TO_BIG_MAP = {  "housing" : "Housing",
                               "electronics" : "Electronics and Computers",
                               "home" : "Home, Garden and Tools",
                               "clothing" : "Clothing, Shoes and Jewelery",
                               "tickets" : "Tickets",
                               "sports" : "Sports and Outdoors",
                               "cars" : "Motorcycles, Scooters and Cars",
                               "others" : "Others"
                            } 