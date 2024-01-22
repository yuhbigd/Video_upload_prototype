DROP TYPE IF EXISTS FILE_STATUS;
CREATE TYPE FILE_STATUS AS ENUM ('DONE', 'PROCESSING', 'CANCEL');
CREATE TABLE if not exists files (
	id serial NOT NULL,
	name text NOT NULL,
	status FILE_STATUS NOT NULL,
	hls_url text,
	dash_url text
);