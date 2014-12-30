# FSFinance GUI (JSF)

This sub-project assembles the FSFinance WebApp. It contains the application's
GUI layer and defines all other layers, necessary for running the application,
as dependencies.

The frontend technology used is JSF with Apache MyFaces as implementation
library.

## Installation and Configuration

The Web application is built using Java Server Faces (JSF) and thus needs an
application server that provides at least a Servlet container (e.g. Tomcat).
Further a database connection provided through a pre-defined javax.sql.DataSource
JNDI-reference is needed. It is expected that the application server provides
a suitable JDBC driver jar.

### Setting up the DataSource

The DataSource is received through JNDI lookup. In Apache Tomcat a JNDI resource
can be configured in e.g. server.xml or context.xml like this:

<pre>
&lt;Resource name="jdbc/FSFinanceDB"
            auth="Container"
            type="javax.sql.DataSource"
            username="dbusername"
            password="dbpassword"
            driverClassName="org.hsql.jdbcDriver"
            url="jdbc:HypersonicSQL:database"
            maxTotal="8"
            maxIdle="4" /&gt;
</pre>

### Setting up the configuration file's location (optional)

The application's configuration file location is also received through JNDI
lookup. In Apache Tomcat the resource can be configured as follows:

<pre>
&lt;Environment name="fsfinance/ConfigFile"
    			value="file:///absolute-path-to-config-file/fsfinance-config.DEV.properties"
    			type="java.lang.String" /&gt;
</pre>

If the JNDI lookup for the configuration file fails a fallback configuration file with
sane default settings, is loaded instead.

### Setting up the logging directory

The application's logging directory is also received through JNDI lookup.
Properties for the config file and logging directory are separated due to
Spring's and Logback's different syntaxes.

<pre>
&lt;Environment name="fsfinance/LogDirectory"
    			value="/absolute-path-to-log-directory/"
    			type="java.lang.String" /&gt;
</pre>

### Setting JSF's PROJECT_STAGE through JNDI (for Development)

Since JSF 2.0 it is possible to override the *javax.faces.PROJECT_STAGE* value defined
in the application's *web.xml* file through JNDI. If the JSF implementation finds a
value under JNDI entry *java:comp/env/jsf/ProjectStage* it is used to set the
PROJECT_STAGE. In Apache Tomcat this can be defined as follows:

<pre>
&lt;Environment name="jsf/ProjectStage"
    			value="Development"
    			type="java.lang.String" /&gt;
</pre>
