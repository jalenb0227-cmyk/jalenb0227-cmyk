// Create a String and an int variable below
let weather = "snowing"
let temp = 32
// console.log the String value
console.log(weather)
// console.log the int * 2
console.log(temp * 2);

// Create a function that will double the inputted number
function doubler (value) {
    let double = value * 2;
    return double
}

// console.log the function with a chosen value as a parameter
console.log(doubler(15));

// build an object of yourself with 3 properties
let me = {
  hometown: "Queens",
  age: 17,
  hobby: "Soccer"
};

// console.log your name
console.log(me.hometown);

 /* create variables for an unordered list of HTML elements
 (one ul, and three li) */

let list = document.createElement("ul");
let listItem1 = document.createElement("li");
let listItem2 = document.createElement("li");
let listItem3 = document.createElement("li");

//insert the object properties through innerText into the li elements

listItem1.innerText = me.hometown;
listItem2.innerText = me.age;
listItem3.innerText = me.hobby;

//append the list items to the ul

list.append(listItem1, listItem2, listItem3);
console.log(list);

// display these within the <div class="collector">
collectDiv = document.querySelector(".collector");
collectDiv.append(list);
