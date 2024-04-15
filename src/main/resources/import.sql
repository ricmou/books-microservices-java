-- books
insert into book(isbn,title,language) values ('978-0321356680','Effective Java','en_GB');
insert into book(isbn,title,language) values ('978-1617292545','Spring Boot in Action','en_GB');
insert into book(isbn,title,language) values ('978-1491900864','Java 8 Pocket Guide','en_US');
insert into book(isbn,title,language) values ('978-0321349606','Java Concurrency in Practice','es_MX');

-- authors
--insert into book_authors(book_id,first_name,last_name) values (1,'Joshua', 'Blosh');
--insert into book_authors(book_id,first_name,last_name) values (2,'Craig', 'Walls');
--insert into book_authors(book_id,first_name,last_name) values (3,'Robert', 'Liguori');
--insert into book_authors(book_id,first_name,last_name) values (3,'Patricia', 'Liguori');
--insert into book_authors(book_id,first_name,last_name) values (4,'Brian', 'Goetz');
--insert into book_authors(book_id,first_name,last_name) values (4,'Joshua', 'Blosh');
--insert into book_authors(book_id,first_name,last_name) values (4,'Joseph', 'Bowbeer');
--insert into book_authors(book_id,first_name,last_name) values (4,'Tim', 'Peierls');

insert into book_descriptions(book_isbn,text,language) values ('978-0321356680','Java that is effective','en_GB');
insert into book_descriptions(book_isbn,text,language) values ('978-1617292545','Spring in winter','en_GB');
insert into book_descriptions(book_isbn,text,language) values ('978-1491900864','Hope your pockets are big','en_GB');
insert into book_descriptions(book_isbn,text,language) values ('978-0321349606','async madness','en_GB');
insert into book_descriptions(book_isbn,text,language) values ('978-0321349606','baguette','fr');

--insert into book_descriptions(book_id,text,language) values (1,'Java that is effective','GB');
--insert into book_descriptions(book_id,text,language) values (2,'Spring in winter','GB');
--insert into book_descriptions(book_id,text,language) values (3,'Hope your pockets are big','GB');
--insert into book_descriptions(book_id,text,language) values (4,'async madness','GB');
--insert into book_descriptions(book_id,text,language) values (4,'baguette','FR');