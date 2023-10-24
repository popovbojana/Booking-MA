--admin
insert into USERS(activated, address, email, name, password, phone_number, role, surname) values (true, 'Bulevar oslobodjenja 12, Novi Sad', 'marko.markovic@gmail.com', 'Marko','marko123', '0654598561', 'ADMIN', 'Markovic')
insert into ADMIN(id) values (1)

--guests
insert into USERS(activated, address, email, name, password, phone_number, role, surname) values (true, 'Nikole Tesle 123, Novi Sad', 'jovan.jovanovic@gmail.com', 'Jovan','jovan123', '0654578561', 'GUEST', 'Jovanovic')
insert into GUESTS(id) values (2)

--owners
insert into USERS(activated, address, email, name, password, phone_number, role, surname) values (true, 'Milosa Crnjanskog 45, Novi Sad', 'pera.peric@gmail.com', 'Pera','pera123', '0638451268', 'OWNER', 'Peric')
insert into OWNERS(id) values (3)

--accommodations
insert into ACCOMMODATIONS(approved, has_changes, max_guests, min_guests, price_per_night, owner_id, amenities, available_from, available_until, description, name, type) values (true, false, 2, 4, 35, 3, 'Wifi, parking', '10.19.2023.', '31.12.2023.', 'Opis', 'Najbolji hotel na svetu', 'Hotel')
insert into OWNERS_ACCOMMODATIONS(accommodations_id, owner_id) values (1, 3)

insert into ACCOMMODATIONS(approved, has_changes, max_guests, min_guests, price_per_night, owner_id, amenities, available_from, available_until, description, name, type) values (true, false, 2, 3, 50, 3, 'Wifi', '10.19.2023.', '31.12.2023.', 'Opis', 'Najbolji motel na svetu', 'Motel')
insert into OWNERS_ACCOMMODATIONS(accommodations_id, owner_id) values (2, 3)
