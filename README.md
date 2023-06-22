# AMITAY STORE SERVER

## Description: 
The server of Amitay's store

## The server address: 
http://localhost:9000

## 🛠 Main technologies:
Java, Spring Boot framework & Spring security, JWT, SQL, H2 DB.

### Installation and running:
Since the database depends on the server, in order to get accurate data and prevent bugs and malfunctions, the server must not be brought down after starting it!
1.	Open a new folder on your computer, to pull the server to her.
2.	In the command line of this folder - run the following command:
      "git clone https://github.com/AmitayGabay/ShoppingWebsiteServer.git"
3.	Run the server in a Java code editor, for example IntelliJ. 
4. The server is listening at http://localhost:9000


### Possible actions:
The server is able to perform the following actions:
In the users section -
Registering a user in the system, deleting a user from the system (and all his favorites/orders if any), providing user details by ID, username or email, and providing details of all users in the system.
The server is able to authenticate the user with a correct username and password and return a JWT token.
The server protects certain paths to which the correct token is not sent, and does not allow access to them.
In the items section -
providing all items, providing a list of items by item name, providing an item by ID, adding an item to the user's favorites, removing an item from the favorites, providing a list of the user's favorites, providing a list of favorites by the name of the favorite item, providing all existing favorites in the system.
In the orders section -
Adding an item to an order (and creating a new open order behind the scenes if necessary), removing an item from the order (and deleting the existing open order if necessary), providing an order by ID, providing the user's list of orders, providing the list of items that exist in a particular order, updating an address To ship an order, and closing an order (changing the status of the order from open to closed so that it will not be possible to add items to it / remove items from it / update a shipping address, and updating the correct amount of items on the site after closing the order).
In addition, the server is able to deliver all existing orders in the system.

Important to know - when starting the server, 10 users and 12 items are created automatically in the H2 database.