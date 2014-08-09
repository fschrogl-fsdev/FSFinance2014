# FSFinance Persistence

This sub-project packages FSFinance's persistence layer. It uses JPA 2.1 with
Hibernate 4 as ORM implementation provider.

## Hibernate Tools

The pom.xml specifies a dedicated profile for invoking Hibernate's Schema-Exporter
and Schema-Validator tools. The profile is called <i>hbmtools</i>. The schema exporter
is bound to Maven's "process-classes" phase, the validator to Maven's "verify"
phase. The can be invoked with the following commands:

<pre>
   mvn process-classes -P hbmtools
   mvn verify -P hbmtools
</pre>
 