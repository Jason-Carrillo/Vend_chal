<H1>MS3 Vending Machine Challenge</h1>
<hr>
<h2>Table of Contents</h2>
<ol>
  <li>Demo</li>
  <li>Objective</li>
  <li>Approach</li>
</ol
<h1>MS3 Vending Machine</h1>
<h1>Demo</h1>

<br>
Launching the program will open up a window that will have the option to Load new items.
<img width="796" alt="Screen Shot 2021-03-23 at 1 25 14 AM" src="https://user-images.githubusercontent.com/68563183/112109447-36b2f900-8b77-11eb-9c48-9aeb429292eb.png">

<br>
When loading new items a new windows opens up a window to allow a user to select the JSON file to open.
<img width="794" alt="Screen Shot 2021-03-23 at 1 25 31 AM" src="https://user-images.githubusercontent.com/68563183/112109588-6104b680-8b77-11eb-9c27-b6bbd6f34988.png">

<br>
Once a JSON file is selected the window will close, and display the JSON file in a row/column format.
<img width="795" alt="Screen Shot 2021-03-23 at 1 25 43 AM" src="https://user-images.githubusercontent.com/68563183/112109651-74b01d00-8b77-11eb-8029-ef98c1badf70.png">

<br>
A user can purchase an item by entering the items position (Letter for the Row, and Number for the Column)<br>
A new window will prompt the user to enter money for the item.
<img width="794" alt="Screen Shot 2021-03-23 at 1 35 30 AM" src="https://user-images.githubusercontent.com/68563183/112110095-0f106080-8b78-11eb-8b25-283d67db17c3.png">

<br>
Once Sufficient funds are entered the page will give the user the purchased item.
<img width="794" alt="Screen Shot 2021-03-23 at 1 26 35 AM" src="https://user-images.githubusercontent.com/68563183/112110291-4f6fde80-8b78-11eb-83e5-7861a4e00a45.png">

<br>
The window will close, and reduce the purchased items amount available per transaction.
<img width="794" alt="Screen Shot 2021-03-23 at 1 26 43 AM" src="https://user-images.githubusercontent.com/68563183/112110316-572f8300-8b78-11eb-890a-33ddd74ec794.png">

<br>
If an item is currently out of stock, a window will let the user know.
<img width="794" alt="Screen Shot 2021-03-23 at 1 50 07 AM" src="https://user-images.githubusercontent.com/68563183/112111655-1c2e4f00-8b7a-11eb-8f38-a8c2d44f84b7.png">

<br>
If a number lower than the price of the item is entered, the window will display alerting insufficient funds.
<img width="794" alt="Screen Shot 2021-03-23 at 1 26 21 AM" src="https://user-images.githubusercontent.com/68563183/112110337-5e569100-8b78-11eb-9d1f-13bb07a5ee55.png">

<br>
If a position that doesn't contain an item is selected an alert will tell the user there is no item at that position.
<img width="794" alt="Screen Shot 2021-03-23 at 1 26 55 AM" src="https://user-images.githubusercontent.com/68563183/112110342-61518180-8b78-11eb-9daa-fc6098f52836.png">

<br>
Example: Loading a large JSON file
<img width="794" alt="Screen Shot 2021-03-23 at 1 51 54 AM" src="https://user-images.githubusercontent.com/68563183/112111847-5bf53680-8b7a-11eb-8bff-6a2038662b2c.png">

<hr>

<h1>Objective</h1>
<ul>
  <li> Provide a loading option for new product lists to update.
  <li> Rows will be Letters.
  <li> Columns will be Numbers.
  <li> User must be able to enter a selection.
  <li> Machine must prompt the user for payment and amount in US dollars.
  <li> If user enters in payment, calculation must occur and be reported.
  <li> Machine must state the current state of the transaction.
  <li> Actions must be cleanly logged for audit purposes.
</ul>

<h1>Approach</h1>
<ol>
  <li>Used JFrame to display a user-friendly interface</li>
  <li>Used JFileChooser to allow a user to select a JSON file to be used</li>
  <li>Parsed JSON string using JSON Object, and parsed specific item Object from the JSON file items array</li>
  <li>Looped through the Items Array to individually load, and add items to the page</li>
  <li>Used GridBag to store specific items in their correct positions(Row/Column)</li>
  
</ol>

