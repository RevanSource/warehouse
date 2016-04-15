--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- Data for Name: address; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO address (id, line1, line2, city, state, country) VALUES (1003, '123', 'Maple Street', 'Mill Valley', 'California', 'USA');
INSERT INTO address (id, line1, line2, city, state, country) VALUES (1005, '4055', 'Madison Ave', 'Seattle', 'Washington', 'USA');


--
-- Name: address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('address_id_seq', 1, false);


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO customer (id, name, phone, email, other_details, address_id) VALUES (1006, 'Jessica Arnold', '8 900 23232', 'jessica-arnold@gmai.com', 'Key Customer', 1005);


--
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('customer_id_seq', 1, false);


--
-- Data for Name: store; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO store (id, name, phone, email, other_details, address_id) VALUES (1004, 'Big Store', '8 800 2000 898', 'mail@big_store.com', 'The great store', 1003);


--
-- Data for Name: customer_order; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO customer_order (id, order_date, planned_delivery_date, actual_delivery_date, status, other_details, store_id, customer_id) VALUES (1008, '2016-03-03 00:00:00', '2016-03-14 00:00:00', NULL, 'Pending', NULL, 1004, 1006);


--
-- Name: customer_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('customer_order_id_seq', 1, false);

--
-- Data for Name: product_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO product_type (id, name, description) VALUES (1, 'Milk', NULL);


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO product (id, name, price, description, currency, other_details, product_type_id) VALUES (1007, 'Healthy Milk', 2, 'Standart 3.2%', 'USD', NULL, 1);


--
-- Data for Name: order_item; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO order_item (id, quantity, other_details, customer_order_id, product_id) VALUES (1009, 10, NULL, 1008, 1007);


--
-- Name: order_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('order_item_id_seq', 1, false);


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('product_id_seq', 1, false);


--
-- Name: product_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('product_type_id_seq', 2, true);


--
-- Data for Name: promotion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO promotion (id, name, date_from, date_to, description, other_details) VALUES (1002, 'Great discount', '2016-03-06 00:00:00', '2016-03-16 00:00:00', 'more 10%', NULL);
