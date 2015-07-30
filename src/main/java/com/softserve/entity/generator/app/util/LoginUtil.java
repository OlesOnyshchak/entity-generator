package com.softserve.entity.generator.app.util;

import com.softserve.entity.generator.salesforce.SalesforceCredentials;
import javassist.NotFoundException;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

public class LoginUtil
{
    private static final Logger logger = Logger.getLogger(LoginUtil.class);

    private static SalesforceCredentials salesforceCredentials;

    public static SalesforceCredentials getPersistedCredentials()
    {
        if (salesforceCredentials == null)
        {
            try
            {
                salesforceCredentials = CredentialsUtil.loadCredentials();
            }
            catch (NotFoundException ex)
            {
                logger.error("User not found");
                System.exit(1);
            }
        }
        return salesforceCredentials;
    }

    public static SalesforceCredentials parseCredentials(String[] credentials)
    {
        Options options = new Options();

        if (credentials.length != 0)
        {
            CommandLineParser parser = new BasicParser();

            CommandLine commandLine = null;

            options.addOption("h", "help", false, "Print help for this application");
            options.addOption("u", "username", true, "Salesforce username");
            options.addOption("p", "password", true, "Password to your organization");
            options.addOption("t", "token", true, "Security token");

            try
            {
                commandLine = parser.parse(options, credentials);

                if (commandLine.hasOption('h'))
                {
                    help(options);
                }

                String username = commandLine.getOptionValue('u');
                String password = commandLine.getOptionValue('p');
                String token = commandLine.getOptionValue('t');

                if (username == null || password == null || token == null)
                {
                    help(options);
                    System.exit(1);
                }

                SalesforceCredentials parsedSalesforceCredentials = new SalesforceCredentials(username, password, token);
                CredentialsUtil.saveCredentials(parsedSalesforceCredentials);
            }
            catch (ParseException ex)
            {
                logger.error("Failed to parse command line parameters");
                help(options);
                System.exit(1);
            }
        }
        return getPersistedCredentials();
    }

    private static void help(Options options)
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Authenticator", options);
    }
}
