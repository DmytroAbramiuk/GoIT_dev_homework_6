CREATE TABLE workers (
	id bigint PRIMARY KEY,
	"name" VARCHAR NOT NULL CHECK (LENGTH("name"::text)>=2 AND LENGTH("name"::text)<=1000),
	birthday DATE NOT NULL CHECK(birthday >= '1900-01-01'),
	"level" VARCHAR NOT NULL CHECK("level" IN ('Trainee', 'Junior', 'Middle', 'Senior')),
	salary INT NOT NULL CHECK (salary >= 100 AND salary <= 100000)
);

CREATE TABLE clients (
	id bigint PRIMARY KEY,
	"name" VARCHAR NOT NULL CHECK (LENGTH("name"::text)>=2 AND LENGTH("name"::text)<=1000)
);

CREATE TABLE projects (
	id bigint PRIMARY KEY,
	client_id bigint,
	start_date DATE,
	finish_date DATE
);

ALTER TABLE projects
ADD CONSTRAINT client_id_fk
FOREIGN KEY (client_id) REFERENCES clients(id)
ON DELETE CASCADE;

CREATE TABLE project_worker (
	project_id bigint NOT NULL,
	worker_id bigint NOT NULL,
	PRIMARY KEY(project_id, worker_id),
	FOREIGN KEY(project_id) REFERENCES projects(id) ON DELETE CASCADE,
	FOREIGN KEY(worker_id) REFERENCES workers(id) ON DELETE CASCADE
);

CREATE SEQUENCE WORKERS_SEQUENCE START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE CLIENTS_SEQUENCE START WITH 1 INCREMENT BY 1;
CREATE sequence PROJECTS_SEQUENCE START WITH 1 INCREMENT BY 1;

ALTER TABLE workers ALTER COLUMN id SET DEFAULT nextval('WORKERS_SEQUENCE');
ALTER TABLE clients ALTER COLUMN id SET DEFAULT nextval('CLIENTS_SEQUENCE');
ALTER TABLE projects ALTER COLUMN id SET DEFAULT nextval('PROJECTS_SEQUENCE');