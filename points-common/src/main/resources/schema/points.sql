CREATE TABLE IF NOT EXISTS points
(
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    slug       VARCHAR NOT NULL,
    name       VARCHAR NOT NULL,
    owner      VARCHAR NOT NULL,
    x          INTEGER NOT NULL,
    y          INTEGER NOT NULL,
    z          INTEGER NOT NULL,
    world      VARCHAR NOT NULL,
    private    INTEGER NOT NULL DEFAULT 1,
    hidden     INTEGER NOT NULL DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
)
