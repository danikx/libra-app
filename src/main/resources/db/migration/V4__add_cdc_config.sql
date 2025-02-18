DO $$ 
BEGIN
    -- Set REPLICA IDENTITY if not already set
    IF (SELECT relreplident FROM pg_class WHERE relname = 'authors') != 'f' THEN
        ALTER TABLE authors REPLICA IDENTITY FULL;
    END IF;
    
    -- Create publication if not exists
    IF NOT EXISTS (SELECT 1 FROM pg_publication WHERE pubname = 'dbz_publication') THEN
        CREATE PUBLICATION dbz_publication FOR TABLE authors;
    END IF;
END $$;