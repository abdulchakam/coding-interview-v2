-- Create sequence for table dog
    CREATE SEQUENCE public.dog_seq
    	INCREMENT BY 1
    	MINVALUE 1
    	MAXVALUE 9223372036854775807
    	START 1
    	CACHE 1
    	NO CYCLE;

-- Query ddl create table dog
    CREATE TABLE public.dog (
        id numeric(20) NOT NULL DEFAULT nextval('dog_seq'::regclass),
        dog_name varchar(200) NOT NULL,
        breed varchar(200) NOT NULL,
       	created_date timestamp(0) NOT NULL,
       	created_by varchar(100) NOT NULL,
       	modified_date timestamp(0) NULL,
       	modified_by varchar(100) NULL,
       	is_deleted bool NOT NULL,
       	CONSTRAINT dog_pk PRIMARY KEY (id)
    );


   -- Create sequence for table sub_breed
CREATE SEQUENCE public.sub_breed_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Query ddl create table sub_breed
	CREATE TABLE public.sub_breed (
    	id numeric(20) NOT NULL DEFAULT nextval('sub_breed_seq'::regclass),
    	dog_id numeric(20) NOT NULL,
    	sub_breed_name varchar(200) NULL,
    	CONSTRAINT sub_breed_pk PRIMARY KEY (id)
    );