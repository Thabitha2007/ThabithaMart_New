MERGE INTO users KEY(username) VALUES
(1,'admin','$2a$10$DowJonesExampleHashReplaceBeforeUse','ADMIN');
-- The application creates the reliable admin hash at startup if absent.
