--
-- PostgreSQL database dump
--

-- Dumped from database version 15.0
-- Dumped by pg_dump version 15.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: warehouse; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA warehouse;


ALTER SCHEMA warehouse OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: client; Type: TABLE; Schema: warehouse; Owner: postgres
--

CREATE TABLE warehouse.client (
    name character varying(100),
    id integer NOT NULL
);


ALTER TABLE warehouse.client OWNER TO postgres;

--
-- Name: client_id_seq; Type: SEQUENCE; Schema: warehouse; Owner: postgres
--

CREATE SEQUENCE warehouse.client_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE warehouse.client_id_seq OWNER TO postgres;

--
-- Name: client_id_seq; Type: SEQUENCE OWNED BY; Schema: warehouse; Owner: postgres
--

ALTER SEQUENCE warehouse.client_id_seq OWNED BY warehouse.client.id;


--
-- Name: log; Type: TABLE; Schema: warehouse; Owner: postgres
--

CREATE TABLE warehouse.log (
    id integer,
    localdatetime timestamp without time zone,
    idproduct integer,
    idclient integer,
    quantity integer,
    priceunit double precision,
    totalprice double precision
);


ALTER TABLE warehouse.log OWNER TO postgres;

--
-- Name: order; Type: TABLE; Schema: warehouse; Owner: postgres
--

CREATE TABLE warehouse."order" (
    product integer,
    id integer NOT NULL,
    client integer,
    quantity integer,
    price double precision
);


ALTER TABLE warehouse."order" OWNER TO postgres;

--
-- Name: order_id_seq; Type: SEQUENCE; Schema: warehouse; Owner: postgres
--

CREATE SEQUENCE warehouse.order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE warehouse.order_id_seq OWNER TO postgres;

--
-- Name: order_id_seq; Type: SEQUENCE OWNED BY; Schema: warehouse; Owner: postgres
--

ALTER SEQUENCE warehouse.order_id_seq OWNED BY warehouse."order".id;


--
-- Name: product; Type: TABLE; Schema: warehouse; Owner: postgres
--

CREATE TABLE warehouse.product (
    name character varying(100),
    id integer NOT NULL,
    quantity integer,
    price double precision
);


ALTER TABLE warehouse.product OWNER TO postgres;

--
-- Name: product_id_seq; Type: SEQUENCE; Schema: warehouse; Owner: postgres
--

CREATE SEQUENCE warehouse.product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE warehouse.product_id_seq OWNER TO postgres;

--
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: warehouse; Owner: postgres
--

ALTER SEQUENCE warehouse.product_id_seq OWNED BY warehouse.product.id;


--
-- Name: client id; Type: DEFAULT; Schema: warehouse; Owner: postgres
--

ALTER TABLE ONLY warehouse.client ALTER COLUMN id SET DEFAULT nextval('warehouse.client_id_seq'::regclass);


--
-- Name: order id; Type: DEFAULT; Schema: warehouse; Owner: postgres
--

ALTER TABLE ONLY warehouse."order" ALTER COLUMN id SET DEFAULT nextval('warehouse.order_id_seq'::regclass);


--
-- Name: product id; Type: DEFAULT; Schema: warehouse; Owner: postgres
--

ALTER TABLE ONLY warehouse.product ALTER COLUMN id SET DEFAULT nextval('warehouse.product_id_seq'::regclass);


--
-- Data for Name: client; Type: TABLE DATA; Schema: warehouse; Owner: postgres
--

COPY warehouse.client (name, id) FROM stdin;
Andreea Onaci	1
Bogdan Doia	2
Petrut Betuel Paul	3
Alin Tcaci	4
\.


--
-- Data for Name: log; Type: TABLE DATA; Schema: warehouse; Owner: postgres
--

COPY warehouse.log (id, localdatetime, idproduct, idclient, quantity, priceunit, totalprice) FROM stdin;
1	2023-05-18 19:50:19.1781	4	1	2	4.34	8.68
2	2023-05-18 19:50:28.474658	2	3	23	6.76	155.48
3	2023-05-18 19:50:40.875576	1	2	20	5.6	112
\.


--
-- Data for Name: order; Type: TABLE DATA; Schema: warehouse; Owner: postgres
--

COPY warehouse."order" (product, id, client, quantity, price) FROM stdin;
4	1	1	2	4.34
2	2	3	23	6.76
1	3	2	20	5.6
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: warehouse; Owner: postgres
--

COPY warehouse.product (name, id, quantity, price) FROM stdin;
paine	3	54	7.5
faina	4	2	4.34
ciocolata	2	22	6.76
lapte	1	27	5.6
\.


--
-- Name: client_id_seq; Type: SEQUENCE SET; Schema: warehouse; Owner: postgres
--

SELECT pg_catalog.setval('warehouse.client_id_seq', 1, false);


--
-- Name: order_id_seq; Type: SEQUENCE SET; Schema: warehouse; Owner: postgres
--

SELECT pg_catalog.setval('warehouse.order_id_seq', 1, false);


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: warehouse; Owner: postgres
--

SELECT pg_catalog.setval('warehouse.product_id_seq', 1, false);


--
-- Name: client client_pk; Type: CONSTRAINT; Schema: warehouse; Owner: postgres
--

ALTER TABLE ONLY warehouse.client
    ADD CONSTRAINT client_pk PRIMARY KEY (id);


--
-- Name: order order_pk; Type: CONSTRAINT; Schema: warehouse; Owner: postgres
--

ALTER TABLE ONLY warehouse."order"
    ADD CONSTRAINT order_pk PRIMARY KEY (id);


--
-- Name: product product_pk; Type: CONSTRAINT; Schema: warehouse; Owner: postgres
--

ALTER TABLE ONLY warehouse.product
    ADD CONSTRAINT product_pk PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

