-- SQLCMD Mode
:setvar DatabaseName "WebPub"

USE master
GO

IF EXISTS (SELECT [name] FROM [master].[sys].[databases] WHERE [name] = N'$(DatabaseName)')
    DROP DATABASE $(DatabaseName);
GO

CREATE DATABASE [WebPub];
GO

USE $(DatabaseName);
GO

CREATE SCHEMA [Thot] AUTHORIZATION [dbo];
GO

