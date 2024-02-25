drop database chatPGV;
create database if not exists chatPGV;

use chatPGV;

create table usuarios(
	id int primary key auto_increment,
    nombre varchar(50),
    contraseña varchar(20),
    tlf INT CHECK (Tlf >= 111111111 AND Tlf <= 999999999)
);

create table mensajes(
	id int primary key auto_increment,
    id_remitente int,
    id_destinatario int,
    txt_Mensaje varchar(200),
    fecha datetime
);

insert into usuarios values (default,"Jose", "123", 928666666),
						 (default, "Ana", "456", 828666666),
                         (default, "Leo", "789", 928478975),
                         (default, "Luis", "PGV", 928678146),
                         (default, "Cinthya", "AED", 928327811),
                         (default, "Noelia", "PGL", 928179966);

