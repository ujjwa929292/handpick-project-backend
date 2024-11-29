CREATE TABLE IF NOT EXISTS Category (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    shortdescription TEXT,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS File (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    file_type VARCHAR(255),
    alt TEXT,
    thumbnailurl VARCHAR(255),
    productid UUID,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Product (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    shortdescription TEXT,
    url VARCHAR(255),
    description TEXT,
    headline VARCHAR(255),
    headlinedescription VARCHAR(255),
    price VARCHAR(1000),
    maincategory_id UUID,
    fileids UUID[],
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (maincategory_id) REFERENCES Category(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Review (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    authorname VARCHAR(255) NOT NULL,
    rating INT NOT NULL,
    title VARCHAR(255),
    description TEXT,
    productid UUID NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (productid) REFERENCES Product(id) ON DELETE CASCADE
);