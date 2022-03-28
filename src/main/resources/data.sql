insert into governorate(id, name) values(1, 'Al Asimah (Kuwait city)');
insert into governorate(id, name) values(2, 'Al Ahmadi');
insert into governorate(id, name) values(3, 'Al Farwaniyah');
insert into governorate(id, name) values(4, 'Al Jahra');
insert into governorate(id, name) values(5, 'Hawalli');
insert into governorate(id, name) values(6, 'Mubarak Al-Kabeer');

insert into area(id, name, governorate_id) values(1, 'Kuwait City',1);
insert into area(id, name, governorate_id) values(2, 'Al Dasmah',1);
insert into area(id, name, governorate_id) values(3, 'Dasman',1);
insert into area(id, name, governorate_id) values(4, 'Sharq',1);
insert into area(id, name, governorate_id) values(5, 'Kifan',1);
insert into area(id, name, governorate_id) values(6, 'Al Khaldiya',1);
insert into area(id, name, governorate_id) values(7, 'Al Salmiya',5);
insert into area(id, name, governorate_id) values(8, 'Al Shaab',5);
insert into area(id, name, governorate_id) values(9, 'Hawalli',5);
insert into area(id, name, governorate_id) values(10, 'Salwa',5);

insert into category(id, description, meta_title, slug, title) 
values(1, 'Shirts for Men', 'Shirts for Men', 'shirts', 'Shirts');
insert into category(id, description, meta_title, slug, title) 
values(2, 'T-shirts for Men', 'T-shirts for Men', 't-shirts', 'T-shirts');

insert into sub_category(id, description, meta_title, slug, title)
values(1, 'Dress shirt perfect for Tuxedo', 'Tuxedo shirt', 'tuxedo', 'Tuxedo');

insert into sub_category(id, description, meta_title, slug, title)
values(2, 'Classic Oxford Button-down shirt', 'Classic shirt', 'classic', 'Classic');

insert into sub_category(id, description, meta_title, slug, title)
values(3, 'Casual Flannel shirt', 'Casual shirt', 'casual', 'Casual');



insert into sub_categories_categories(category_id, sub_category_id)
values(1, 1);
insert into sub_categories_categories(category_id, sub_category_id)
values(1, 2);
insert into sub_categories_categories(category_id, sub_category_id)
values(1, 3);


insert into sub_category(id, description, meta_title, slug, title)
values(4, 'The Perfect Polo', 'Polo design', 'polo', 'Polo');

insert into sub_category(id, description, meta_title, slug, title)
values(5, 'Henley', 'The true-to-fit Henley', 'henley', 'Henley');

insert into sub_category(id, description, meta_title, slug, title)
values(6, 'V-Neck t-shirts', 'V-neck t-shirts', 'v-neck', 'V-Neck');


insert into sub_categories_categories(category_id, sub_category_id)
values(1, 4);
insert into sub_categories_categories(category_id, sub_category_id)
values(1, 5);
insert into sub_categories_categories(category_id, sub_category_id)
values(2, 4);
insert into sub_categories_categories(category_id, sub_category_id)
values(2, 5);
insert into sub_categories_categories(category_id, sub_category_id)
values(2, 6);