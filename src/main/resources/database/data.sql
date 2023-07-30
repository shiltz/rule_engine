insert into public.address (id, address_line)
values (1, '1 Lone Street');

insert into profile (id, name, surname, address_id)
values (1, 'Shilton', 'Naicker', 1);

insert into rule_set_strict (id, name)
values (1, 'STRICT');

insert into rule_set_lenient (id, name)
values (2, 'LENIENT');


insert into validation_rule (id, column_name, constraint_type, table_name)
values (1, 'name', 'REQUIRED', 'profile');
insert into rule_set_strict_rule_set (rule_set_strict_id, rule_set_id)
values (1, 1);

insert into validation_rule (id, column_name, constraint_type, table_name)
values (2, 'surname', 'REQUIRED', 'profile');
insert into rule_set_strict_rule_set (rule_set_strict_id, rule_set_id)
values (1, 2);

insert into validation_rule (id, column_name, constraint_type, table_name)
values (3, 'address_line', 'REQUIRED', 'address');
insert into rule_set_strict_rule_set (rule_set_strict_id, rule_set_id)
values (1, 3);


insert into rule_set_lenient_rule_set (rule_set_lenient_id, rule_set_id)
values (2, 1);