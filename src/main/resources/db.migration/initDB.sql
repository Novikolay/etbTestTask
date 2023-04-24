DROP TABLE IF EXISTS public.customers;
DROP TABLE IF EXISTS public.bills;
DROP TABLE IF EXISTS public.actions;

CREATE TABLE IF NOT EXISTS public.customers (
    id   serial NOT NULL,
    name character varying(100) NOT NULL,
    CONSTRAINT customer_id_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.bills (
    id         serial NOT NULL,
    customerId integer NOT NULL,
    amount     integer NOT NULL,
    CONSTRAINT bill_id_pk PRIMARY KEY (id)
);

ALTER TABLE public.bills
    ADD CONSTRAINT fk_bill_customers FOREIGN KEY (customerId)
        REFERENCES public.customers (id) ON UPDATE CASCADE ON DELETE RESTRICT;

CREATE TABLE IF NOT EXISTS public.actions (
    id serial NOT NULL,
    dateTime timestamp without time zone NOT NULL,
    billId integer NOT NULL,
    actionType integer NOT NULL,
    amount     integer NOT NULL,
    CONSTRAINT action_id_pk PRIMARY KEY (id)
);