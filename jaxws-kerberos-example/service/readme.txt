Add the followint to JBoss EAP 6.1 to standalone.xml file and deploy the WAR file

<system-properties>
    <property name="java.security.krb5.conf" value="/etc/krb5.conf"/>
    <property name="java.security.krb5.debug" value="true"/>
    <property name="java.security.disable.secdomain.option" value="true"/>
    <property name="javax.security.auth.useSubjectCredsOnly" value="false"/>
</system-properties>


<security-domain name="host" cache-type="default">
    <authentication>
        <login-module code="Kerberos" flag="required">
            <module-option name="storeKey" value="true"/>
            <module-option name="useKeyTab" value="true"/>
            <module-option name="keyTab" value="/path/to/bob.keytab"/>
            <module-option name="principal" value="bob/service.example.com"/>
            <module-option name="doNotPrompt" value="true"/>
            <module-option name="debug" value="true"/>
        </login-module>
    </authentication>
</security-domain>


For client usage from Jboss, check out the blogs https://developer.jboss.org/docs/DOC-52936
