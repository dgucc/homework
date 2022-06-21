-- SQLCMD Mode
:setvar DatabaseName "WebPub"

USE master
GO

IF EXISTS (SELECT [name] FROM [master].[sys].[databases] WHERE [name] = N'$(DatabaseName)')
	ALTER DATABASE [WebPub] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE $(DatabaseName);
GO

CREATE DATABASE [WebPub];
GO

USE $(DatabaseName);
GO

CREATE SCHEMA [Thot] AUTHORIZATION [dbo];
GO


CREATE TABLE [Thot].[Publication](
    [id] [int] IDENTITY (1, 1) NOT NULL,
    [publishDate] [datetime] NOT NULL,
    [type] [int] NOT NULL,
    [legislativeLevel] [int] NOT NULL, 
    [modificationDate] [datetime] NOT NULL DEFAULT (GETDATE()),
    CONSTRAINT "PK_Publication" PRIMARY KEY CLUSTERED ("id")
);
GO

CREATE TABLE [Thot].[Topic](
    [id] [int] NOT NULL,
    [nl] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [fr] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [de] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    [en] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    CONSTRAINT "PK_Topic" PRIMARY KEY CLUSTERED ("id")
) 
GO

CREATE TABLE [Thot].[Referenced_Topic](
    [id] [int]IDENTITY (1, 1) NOT NULL,
    [publicationId] [int] NOT NULL,
    [topicId] [int] NOT NULL,
    CONSTRAINT "PK_Referenced_Topic" PRIMARY KEY CLUSTERED ("id")
) 
GO
ALTER TABLE [Thot].[Referenced_Topic] ADD CONSTRAINT [FK_RefTopic_Publication] FOREIGN KEY ([publicationId]) REFERENCES [thot].[Publication] ([id])
GO
ALTER TABLE [Thot].[Referenced_Topic] ADD CONSTRAINT [FK_RefTopic_Topic] FOREIGN KEY ([publicationId]) REFERENCES [thot].[Topic] ([id])
GO
CREATE  INDEX "IDX_RefTopic_Bridge" ON [Thot].[Referenced_Topic]("publicationId","topicId")
GO

CREATE TABLE [Thot].[Title](
    [id] [int] NOT NULL,
    [nl] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [fr] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [de] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    [en] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    [modificationDate] [datetime] NOT NULL DEFAULT (GETDATE()),
    [publicationId][int] NOT NULL,
    CONSTRAINT "PK_Title" PRIMARY KEY CLUSTERED ("id")
) 
GO
ALTER TABLE [Thot].[Title] ADD CONSTRAINT [FK_Title_Publication] FOREIGN KEY ([publicationId]) REFERENCES [thot].[Publication] ([id])

CREATE TABLE [Thot].[Header](
    [id] [int] NOT NULL,
    [nl] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [fr] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [de] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    [en] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    [modificationDate] [datetime] NOT NULL DEFAULT (GETDATE()),
    CONSTRAINT "PK_Header" PRIMARY KEY CLUSTERED ("id")
) 
GO

CREATE TABLE [Thot].[Document](
    [id] [int] NOT NULL,
    [type] [int] NOT NULL,
    [language] [int] NOT NULL,
    [fileType] [int] NOT NULL,
    [file] [varbinary](max) NOT NULL,
    [modificationDate] [datetime] NOT NULL DEFAULT (GETDATE()),
    CONSTRAINT "PK_Document" PRIMARY KEY CLUSTERED ("id")
) 
GO

CREATE TABLE [Thot].[Publication_Type] (
    [id] [int] NOT NULL,
    [nl] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [fr] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [de] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    [en] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    CONSTRAINT "PK_Publication_Type" PRIMARY KEY CLUSTERED ("id")
)
GO

CREATE TABLE [Thot].[Legislative_Level] (
    [id] [int] NOT NULL,
    [nl] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [fr] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [de] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    [en] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    CONSTRAINT "PK_Legislative_Level" PRIMARY KEY CLUSTERED ("id")
)
GO

CREATE TABLE [Thot].[Document_Type] (
    [id] [int] NOT NULL,
    [nl] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [fr] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [de] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    [en] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    CONSTRAINT "PK_Document_Type" PRIMARY KEY CLUSTERED ("id")
)
GO

CREATE TABLE [Thot].[File_Type] (
    [id] [int] NOT NULL,
    [nl] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [fr] [nvarchar](100) COLLATE DATABASE_DEFAULT NOT NULL,
    [de] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    [en] [nvarchar](100) COLLATE DATABASE_DEFAULT,
    CONSTRAINT "PK_File_Type" PRIMARY KEY CLUSTERED ("id")
)
GO

CREATE TABLE [Thot].[Language] (
    [id] [int] NOT NULL,
    [nl] [char](2) COLLATE DATABASE_DEFAULT NOT NULL,
    [fr] [char](2) COLLATE DATABASE_DEFAULT NOT NULL,
    [de] [char](2) COLLATE DATABASE_DEFAULT,
    [en] [char](2) COLLATE DATABASE_DEFAULT,
    CONSTRAINT "PK_Language" PRIMARY KEY CLUSTERED ("id")
)
GO

USE master
GO

ALTER DATABASE [WebPub] SET MULTI_USER;
GO
