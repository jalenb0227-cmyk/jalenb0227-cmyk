///////////////////////////////////////// Object Literal Syntax
/* const book = {
    title: "Nineteen Eighty-Four",
    author: "George Orwell",
    genre: "Dystopian Future",
    edition: 1,
    published: 1949
};

console.log(book);
console.log(book.genre); */


////////////////////////////////////////// Object Prototype Constructor
const book = new Object();
book.title = 'Nineteen Eighty-Four';
book.author = 'George Orwell';
book.genre = 'Dystopian Future';
book.edition = 1;
book.published = 1949;

console.log(book);
console.log(book.author); 

/////////////////////////////////////////// Constructor Function 
const Book = function (title, author, genre, edition, published) {
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.edition = edition;
    this.published = published;

};

const order = new Book('Nineteen Eighty-Four','George Orwell','Dystopian Future',1, 1949 );
console.log(order);
console.log(order.published);