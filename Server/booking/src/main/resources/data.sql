--admin
insert into USERS(activated, address, email, name, password, phone_number, role, surname) values (true, 'Bulevar oslobodjenja 12, Novi Sad', 'marko.markovic@gmail.com', 'Marko','$2a$12$nkYUpdFD23kArmZ8MvSiYeXna3nJKhVd.2ZIcSmxSDIugjtTBehyy', '0654598561', 'ADMIN', 'Markovic')
insert into ADMIN(id) values (1)

--guests
insert into USERS(activated, address, email, name, password, phone_number, role, surname) values (true, 'Nikole Tesle 123, Novi Sad', 'jovan.jovanovic@gmail.com', 'Jovan','$2a$12$HmWbFvJaNlSSPQEAI0GHNOJkCpojaeN0hJEYRhrmWJZrEkly95MbK', '0654578561', 'GUEST', 'Jovanovic')
insert into GUESTS(id) values (2)

--owners
insert into USERS(activated, address, email, name, password, phone_number, role, surname) values (true, 'Milosa Crnjanskog 45, Novi Sad', 'pera.peric@gmail.com', 'Pera','$2a$12$Htpvzxt7KGIgMG5MCstm1e3fap1sOjw/CKuDHRkUiTbZKNpQH.reS', '0638451268', 'OWNER', 'Peric')
insert into OWNERS(id) values (3)

--accommodations
insert into ACCOMMODATIONS(approved, cancellation_deadline_in_days, has_changes, max_guests, min_guests, price_type, change_id, owner_id, amenities, description, name, type) values (true, 5, false, 4, 2, 'PER_GUEST', null, 3, 'Wifi, parking', 'Opis', 'Najbolji hotel na svetu', 'Hotel')
insert into AVAILABILITY_PRICES(amount, accommodation_id, date_from, date_until) values (20, 1, '2023-10-20T00:00:00', '2023-10-21T00:00:00')

insert into ACCOMMODATIONS(approved, cancellation_deadline_in_days, has_changes, max_guests, min_guests, price_type, change_id, owner_id, amenities, description, name, type) values (true, 3, false, 5, 3, 'PER_UNIT', null, 3, 'Wifi', 'Opis', 'Najbolji motel na svetu', 'Motel')
insert into AVAILABILITY_PRICES(amount, accommodation_id, date_from, date_until) values (30, 2, '2023-10-20T00:00:00', '2023-10-31T00:00:00')
insert into AVAILABILITY_PRICES(amount, accommodation_id, date_from, date_until) values (20, 2, '2023-10-31T00:00:00', '2023-11-15T00:00:00')