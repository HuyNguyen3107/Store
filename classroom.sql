--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: answers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.answers (
    id integer NOT NULL,
    content character varying(255) NOT NULL,
    homework_id integer NOT NULL,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now(),
    user_id integer NOT NULL
);


ALTER TABLE public.answers OWNER TO postgres;

--
-- Name: answers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.answers ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.answers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: classrooms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.classrooms (
    id integer NOT NULL,
    course_id integer,
    end_day character varying(50) NOT NULL,
    name character varying(200) NOT NULL,
    schedule character varying(200) NOT NULL,
    start_day character varying(50) NOT NULL,
    student_number integer NOT NULL,
    teacher_id integer,
    created_at timestamp with time zone DEFAULT now(),
    updated_at timestamp with time zone DEFAULT now()
);


ALTER TABLE public.classrooms OWNER TO postgres;

--
-- Name: classrooms_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.classrooms ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.classrooms_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: courses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.courses (
    id integer NOT NULL,
    name character varying(200) NOT NULL,
    price character varying(15) NOT NULL,
    lesson_number integer NOT NULL,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now()
);


ALTER TABLE public.courses OWNER TO postgres;

--
-- Name: courses_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.courses ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.courses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: documents; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documents (
    id integer NOT NULL,
    title character varying(200) NOT NULL,
    description character varying(1000) NOT NULL,
    content character varying(255) NOT NULL,
    course_id integer,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now()
);


ALTER TABLE public.documents OWNER TO postgres;

--
-- Name: documents_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.documents ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.documents_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: homeworks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.homeworks (
    id integer NOT NULL,
    title character varying(200) NOT NULL,
    content character varying(255) NOT NULL,
    class_id integer NOT NULL,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now()
);


ALTER TABLE public.homeworks OWNER TO postgres;

--
-- Name: homeworks_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.homeworks ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.homeworks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: password_tokens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.password_tokens (
    id integer NOT NULL,
    reset_token character varying(200) NOT NULL,
    expired character varying(300) NOT NULL,
    user_id integer NOT NULL,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now()
);


ALTER TABLE public.password_tokens OWNER TO postgres;

--
-- Name: password_tokens_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.password_tokens ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.password_tokens_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: permissions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permissions (
    id integer NOT NULL,
    value character varying(100) NOT NULL,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now()
);


ALTER TABLE public.permissions OWNER TO postgres;

--
-- Name: permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.permissions ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now()
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.roles ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: roles_permissions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles_permissions (
    id integer NOT NULL,
    role_id integer,
    permission_id integer,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now()
);


ALTER TABLE public.roles_permissions OWNER TO postgres;

--
-- Name: roles_permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.roles_permissions ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.roles_permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: students; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.students (
    id integer NOT NULL,
    user_id integer NOT NULL,
    status character varying(100) NOT NULL,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now()
);


ALTER TABLE public.students OWNER TO postgres;

--
-- Name: students_classrooms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.students_classrooms (
    id integer NOT NULL,
    student_id integer,
    class_id integer,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now()
);


ALTER TABLE public.students_classrooms OWNER TO postgres;

--
-- Name: students_classes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.students_classrooms ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.students_classes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: students_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.students ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.students_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: teachers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.teachers (
    id integer NOT NULL,
    user_id integer NOT NULL,
    status character varying(100) NOT NULL,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now()
);


ALTER TABLE public.teachers OWNER TO postgres;

--
-- Name: teachers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.teachers ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.teachers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: user_otps; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_otps (
    id integer NOT NULL,
    otp character varying(6) NOT NULL,
    expired character varying(300) NOT NULL,
    user_id integer NOT NULL,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now()
);


ALTER TABLE public.user_otps OWNER TO postgres;

--
-- Name: user_otps_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.user_otps ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_otps_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    email character varying(200) NOT NULL,
    password character varying(200) NOT NULL,
    status character varying(100),
    token character varying(500),
    phone character varying(10),
    address character varying(200),
    created_at timestamp(6) with time zone DEFAULT now(),
    updated_at timestamp(6) with time zone DEFAULT now()
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: users_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_roles (
    id integer NOT NULL,
    user_id integer,
    role_id integer,
    created_at time with time zone DEFAULT now(),
    updated_at time with time zone DEFAULT now()
);


ALTER TABLE public.users_roles OWNER TO postgres;

--
-- Name: users_roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.users_roles ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: answers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.answers (id, content, homework_id, created_at, updated_at, user_id) FROM stdin;
16	ok	10	12:16:48.795296+07	12:16:48.795296+07	3
\.


--
-- Data for Name: classrooms; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.classrooms (id, course_id, end_day, name, schedule, start_day, student_number, teacher_id, created_at, updated_at) FROM stdin;
15	8	30/06/2025	JS Pro	3 5 7 & 04:00 CH	10/04/2025	60	\N	2025-05-03 14:13:18.826755+07	2025-05-04 12:28:03.973013+07
\.


--
-- Data for Name: courses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.courses (id, name, price, lesson_number, created_at, updated_at) FROM stdin;
8	Java	500000	50	12:12:37.936243+07	12:12:37.936243+07
\.


--
-- Data for Name: documents; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.documents (id, title, description, content, course_id, created_at, updated_at) FROM stdin;
5	JS nang cao	Js tu co ban den nang cao	content ne hihi	8	14:17:17.838348+07	04:52:45.056029+00
\.


--
-- Data for Name: homeworks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.homeworks (id, title, content, class_id, created_at, updated_at) FROM stdin;
10	ok	ok	15	12:16:34.738196+07	12:16:34.738196+07
\.


--
-- Data for Name: password_tokens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.password_tokens (id, reset_token, expired, user_id, created_at, updated_at) FROM stdin;
8	196950c21ea4G08LA0KaucOoQyegel7FU3l5h	1746257622474	3	07:48:01.175972+00	07:28:42.480734+00
\.


--
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.permissions (id, value, created_at, updated_at) FROM stdin;
1	users.view	16:45:39.890778+07	\N
2	users.create	16:45:39.890778+07	\N
3	users.update	16:45:39.890778+07	\N
4	users.delete	16:45:39.890778+07	\N
6	answers.view	15:05:27.305503+07	15:05:27.305503+07
7	answers.create	15:05:27.305503+07	15:05:27.305503+07
8	answers.update	15:05:27.305503+07	15:05:27.305503+07
9	answers.delete	15:05:27.305503+07	15:05:27.305503+07
10	users.update_roles	15:36:33.337349+07	15:36:33.337349+07
11	classrooms.view	15:43:38.828716+07	15:43:38.828716+07
12	classrooms.create	15:43:38.828716+07	15:43:38.828716+07
13	classrooms.update	15:43:38.828716+07	15:43:38.828716+07
14	classrooms.delete	15:43:38.828716+07	15:43:38.828716+07
15	classrooms.add_students	15:43:38.828716+07	15:43:38.828716+07
16	classrooms.remove_students	15:43:38.828716+07	15:43:38.828716+07
17	courses.view	16:11:52.646747+07	16:11:52.646747+07
18	courses.create	16:11:52.646747+07	16:11:52.646747+07
19	courses.update	16:11:52.646747+07	16:11:52.646747+07
20	courses.delete	16:11:52.646747+07	16:11:52.646747+07
21	documents.view	16:15:03.292086+07	16:15:03.292086+07
22	documents.create	16:15:03.292086+07	16:15:03.292086+07
23	documents.update	16:15:03.292086+07	16:15:03.292086+07
24	documents.delete	16:15:03.292086+07	16:15:03.292086+07
25	homeworks.view	16:23:38.322091+07	16:23:38.322091+07
26	homeworks.create	16:23:38.322091+07	16:23:38.322091+07
27	homeworks.update	16:23:38.322091+07	16:23:38.322091+07
28	homeworks.delete	16:23:38.322091+07	16:23:38.322091+07
29	students.view	16:28:24.412969+07	16:28:24.412969+07
30	students.create	16:28:24.412969+07	16:28:24.412969+07
31	students.update	16:28:24.412969+07	16:28:24.412969+07
32	students.delete	16:28:24.412969+07	16:28:24.412969+07
33	teachers.view	16:28:59.462685+07	16:28:59.462685+07
34	teachers.create	16:28:59.462685+07	16:28:59.462685+07
35	teachers.update	16:28:59.462685+07	16:28:59.462685+07
36	teachers.delete	16:28:59.462685+07	16:28:59.462685+07
37	roles.update_permissions	16:31:39.321534+07	16:31:39.321534+07
38	permissions.view	14:25:03.134655+07	14:25:03.134655+07
39	permissions.create	14:25:03.134655+07	14:25:03.134655+07
40	permissions.update	14:25:03.134655+07	14:25:03.134655+07
41	permissions.delete	14:25:03.134655+07	14:25:03.134655+07
42	roles.view	14:25:03.134655+07	14:25:03.134655+07
43	roles.update	14:25:03.134655+07	14:25:03.134655+07
44	roles.update	14:25:03.134655+07	14:25:03.134655+07
45	roles.delete	14:25:03.134655+07	14:25:03.134655+07
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name, created_at, updated_at) FROM stdin;
1	Admin	16:46:24.661085+07	16:46:24.661085+07
2	Teacher	16:46:24.661085+07	16:46:24.661085+07
\.


--
-- Data for Name: roles_permissions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles_permissions (id, role_id, permission_id, created_at, updated_at) FROM stdin;
42	1	1	07:37:18.292485+00	07:37:18.29148+00
43	1	2	07:37:18.306766+00	07:37:18.29148+00
44	1	3	07:37:18.308767+00	07:37:18.29148+00
45	1	4	07:37:18.309769+00	07:37:18.29148+00
46	1	6	07:37:18.311852+00	07:37:18.29148+00
47	1	7	07:37:18.313856+00	07:37:18.29148+00
48	1	8	07:37:18.315858+00	07:37:18.29148+00
49	1	9	07:37:18.316856+00	07:37:18.29148+00
50	1	10	07:37:18.317856+00	07:37:18.29148+00
51	1	11	07:37:18.318946+00	07:37:18.29148+00
52	1	12	07:37:18.319858+00	07:37:18.29148+00
53	1	13	07:37:18.320981+00	07:37:18.29148+00
54	1	14	07:37:18.321986+00	07:37:18.29148+00
55	1	15	07:37:18.323029+00	07:37:18.29148+00
56	1	16	07:37:18.323029+00	07:37:18.29148+00
57	1	17	07:37:18.323986+00	07:37:18.29148+00
58	1	18	07:37:18.324986+00	07:37:18.29148+00
59	1	19	07:37:18.326987+00	07:37:18.29148+00
60	1	20	07:37:18.328091+00	07:37:18.29148+00
61	1	21	07:37:18.329105+00	07:37:18.29148+00
62	1	22	07:37:18.330595+00	07:37:18.29148+00
63	1	23	07:37:18.332424+00	07:37:18.29148+00
64	1	24	07:37:18.334528+00	07:37:18.29148+00
65	1	25	07:37:18.335855+00	07:37:18.29148+00
66	1	26	07:37:18.337871+00	07:37:18.29148+00
67	1	27	07:37:18.33897+00	07:37:18.29148+00
68	1	28	07:37:18.339867+00	07:37:18.29148+00
69	1	29	07:37:18.34069+00	07:37:18.29148+00
70	1	30	07:37:18.34069+00	07:37:18.29148+00
71	1	31	07:37:18.341916+00	07:37:18.29148+00
72	1	32	07:37:18.342946+00	07:37:18.29148+00
73	1	33	07:37:18.343922+00	07:37:18.29148+00
74	1	34	07:37:18.344923+00	07:37:18.29148+00
75	1	35	07:37:18.345924+00	07:37:18.29148+00
76	1	36	07:37:18.347923+00	07:37:18.29148+00
77	1	37	07:37:18.348924+00	07:37:18.29148+00
78	1	38	07:37:18.349923+00	07:37:18.29148+00
79	1	39	07:37:18.350939+00	07:37:18.29148+00
80	1	40	07:37:18.352944+00	07:37:18.29148+00
81	1	41	07:37:18.353944+00	07:37:18.29148+00
82	1	42	07:37:18.354944+00	07:37:18.29148+00
83	1	43	07:37:18.355947+00	07:37:18.29148+00
84	1	44	07:37:18.356944+00	07:37:18.29148+00
85	1	45	07:37:18.357943+00	07:37:18.29148+00
86	2	1	17:58:04.711693+00	17:58:04.709693+00
87	2	2	17:58:04.744074+00	17:58:04.709693+00
88	2	3	17:58:04.746972+00	17:58:04.709693+00
\.


--
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.students (id, user_id, status, created_at, updated_at) FROM stdin;
7	3	active	12:18:14.596697+07	12:18:14.596697+07
\.


--
-- Data for Name: students_classrooms; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.students_classrooms (id, student_id, class_id, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: teachers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.teachers (id, user_id, status, created_at, updated_at) FROM stdin;
3	5	active	10:39:12.328295+07	09:35:48.04625+00
8	3	active	00:48:44.576623+07	00:48:44.576623+07
\.


--
-- Data for Name: user_otps; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_otps (id, otp, expired, user_id, created_at, updated_at) FROM stdin;
4	384961	1746257729446	3	04:02:18.972691+00	07:30:29.450853+00
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, name, email, password, status, token, phone, address, created_at, updated_at) FROM stdin;
3	Huy Nguyen	levi2k3ds@gmail.com	$2a$10$UwcvMgDtoP9OCpUM5ZZj4eDy2DOc7yOP0OSnj4hyClmzhyHShvjD.	active	196950dc3bfQTwHbamN5xe77bQKbUGeCobgp8	0968972916	Hoai Duc	2025-03-30 10:00:30.214251+07	2025-05-03 14:30:29.44268+07
5	Hoang An	anhoi@gmail.com	$2a$10$rpZi3t7SQbdBoHkuh2JNVOQhJXEVuEMBEuBVXighP0DJZa.rVKX/W	active	\N	0979952319	Phu Tho	2025-04-27 16:03:44.291237+07	2025-04-27 16:03:44.291237+07
\.


--
-- Data for Name: users_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_roles (id, user_id, role_id, created_at, updated_at) FROM stdin;
10	3	1	08:37:11.328095+00	08:37:11.328095+00
\.


--
-- Name: answers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.answers_id_seq', 16, true);


--
-- Name: classrooms_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.classrooms_id_seq', 16, true);


--
-- Name: courses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.courses_id_seq', 8, true);


--
-- Name: documents_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.documents_id_seq', 6, true);


--
-- Name: homeworks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.homeworks_id_seq', 10, true);


--
-- Name: password_tokens_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.password_tokens_id_seq', 8, true);


--
-- Name: permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.permissions_id_seq', 45, true);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 4, true);


--
-- Name: roles_permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_permissions_id_seq', 91, true);


--
-- Name: students_classes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.students_classes_id_seq', 19, true);


--
-- Name: students_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.students_id_seq', 7, true);


--
-- Name: teachers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.teachers_id_seq', 8, true);


--
-- Name: user_otps_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_otps_id_seq', 4, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 7, true);


--
-- Name: users_roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_roles_id_seq', 11, true);


--
-- Name: answers answers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.answers
    ADD CONSTRAINT answers_pkey PRIMARY KEY (id);


--
-- Name: classrooms classrooms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.classrooms
    ADD CONSTRAINT classrooms_pkey PRIMARY KEY (id);


--
-- Name: courses courses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT courses_pkey PRIMARY KEY (id);


--
-- Name: documents documents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents
    ADD CONSTRAINT documents_pkey PRIMARY KEY (id);


--
-- Name: users email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT email UNIQUE (email);


--
-- Name: homeworks homeworks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.homeworks
    ADD CONSTRAINT homeworks_pkey PRIMARY KEY (id);


--
-- Name: password_tokens password_tokens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.password_tokens
    ADD CONSTRAINT password_tokens_pkey PRIMARY KEY (id);


--
-- Name: permissions permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT permissions_pkey PRIMARY KEY (id);


--
-- Name: roles_permissions roles_permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_permissions
    ADD CONSTRAINT roles_permissions_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: students_classrooms students_classes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students_classrooms
    ADD CONSTRAINT students_classes_pkey PRIMARY KEY (id);


--
-- Name: students students_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_pkey PRIMARY KEY (id);


--
-- Name: students students_unique_user_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_unique_user_id UNIQUE (user_id);


--
-- Name: teachers teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (id);


--
-- Name: teachers teachers_unique_user_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_unique_user_id UNIQUE (user_id);


--
-- Name: user_otps user_otps_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_otps
    ADD CONSTRAINT user_otps_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users_roles users_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (id);


--
-- Name: answers answers_foreign_homework_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.answers
    ADD CONSTRAINT answers_foreign_homework_id FOREIGN KEY (homework_id) REFERENCES public.homeworks(id) NOT VALID;


--
-- Name: answers answers_foreign_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.answers
    ADD CONSTRAINT answers_foreign_user_id FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


--
-- Name: documents documents_foreign_course_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents
    ADD CONSTRAINT documents_foreign_course_id FOREIGN KEY (course_id) REFERENCES public.courses(id) NOT VALID;


--
-- Name: students_classrooms fk8jwno9hx21jsaae3qf0p02a1q; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students_classrooms
    ADD CONSTRAINT fk8jwno9hx21jsaae3qf0p02a1q FOREIGN KEY (class_id) REFERENCES public.classrooms(id);


--
-- Name: homeworks fk8lv6l68002a716p9cvuny8igi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.homeworks
    ADD CONSTRAINT fk8lv6l68002a716p9cvuny8igi FOREIGN KEY (class_id) REFERENCES public.classrooms(id);


--
-- Name: classrooms fkbyl3mbf2j46aa8i242b6gxjg1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.classrooms
    ADD CONSTRAINT fkbyl3mbf2j46aa8i242b6gxjg1 FOREIGN KEY (teacher_id) REFERENCES public.teachers(id);


--
-- Name: classrooms fkptrcjflhvj48duhcag4f3x9j0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.classrooms
    ADD CONSTRAINT fkptrcjflhvj48duhcag4f3x9j0 FOREIGN KEY (course_id) REFERENCES public.courses(id);


--
-- Name: password_tokens password_tokens_foreign_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.password_tokens
    ADD CONSTRAINT password_tokens_foreign_user_id FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


--
-- Name: roles_permissions roles_permissions_foreign_permission_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_permissions
    ADD CONSTRAINT roles_permissions_foreign_permission_id FOREIGN KEY (permission_id) REFERENCES public.permissions(id) NOT VALID;


--
-- Name: roles_permissions roles_permissions_foreign_role_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_permissions
    ADD CONSTRAINT roles_permissions_foreign_role_id FOREIGN KEY (role_id) REFERENCES public.roles(id) NOT VALID;


--
-- Name: students_classrooms students_classes_foreign_student_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students_classrooms
    ADD CONSTRAINT students_classes_foreign_student_id FOREIGN KEY (student_id) REFERENCES public.students(id) NOT VALID;


--
-- Name: students students_foreign_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_foreign_user_id FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


--
-- Name: teachers teachers_foreign_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_foreign_user_id FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


--
-- Name: user_otps user_otps_foreign_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_otps
    ADD CONSTRAINT user_otps_foreign_user_id FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


--
-- Name: users_roles users_roles_foreign_role_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_foreign_role_id FOREIGN KEY (role_id) REFERENCES public.roles(id) NOT VALID;


--
-- Name: users_roles users_roles_foreign_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_foreign_user_id FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


--
-- PostgreSQL database dump complete
--

