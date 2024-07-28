-- Create the Book table
CREATE TABLE Book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INT,
    isbn VARCHAR(20) NOT NULL,
    UNIQUE (isbn)
);

-- Create the Patron table
CREATE TABLE Patron (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_information VARCHAR(255)
);

-- Create the BorrowingRecord table
CREATE TABLE BorrowingRecord (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT,
    patron_id BIGINT,
    borrow_date DATE,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES Book(id),
    FOREIGN KEY (patron_id) REFERENCES Patron(id)
);
