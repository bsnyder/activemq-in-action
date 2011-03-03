ActiveMQ In Action 
  by Bruce Snyder, Rob Davies and Dejan Bosanac 
===============================================

Thank you for downloading the example source code from the book, ActiveMQ In
Action. More information about this book can be found at the Manning 
website:  

  http://manning.com/snyder/ 

More information about Apache ActiveMQ can be found at the Apache Software
Foundation website: 

  http://activemq.apache.org/


About The Book
--------------
ActiveMQ In Action is for software architects, developers, and integrators
interested in enterprise message queuing in general and ActiveMQ in
particular. This book is designed to serve as part introduction and part
reference for both beginners and experienced application developers. It begins
with an introduction to ActiveMQ and a high-level overview of JMS followed by
a progressively deeper dive into ActiveMQ as the book advances.

The concepts discussed throughout this book assume that the reader possesses
enough knowledge of Java EE to design and develop applications. Though such
knowledge is not a strict requirement, it will make it easier to grasp many of
the con- cepts touched upon throughout the chapters. Chapter 9 even discusses
using ActiveMQ with languages other than Java, including C++, C#, JavaScript,
Perl, PHP, Python, and Ruby.


Prerequisites for the Source Code
---------------------------------
To build the source code, the following items are required: 

* Sun Java 1.5 or greater 
* Maven 2.2.x or greater 
* A broadband internet connection 


Building the Source Code 
------------------------
Building the example source code is actually quite straighforward. From the
command line, simply run the following Maven command: 

  $ mvn clean install 

This command will automatically determine and download all dependencies for
the build from the necessary remote Maven repostories , compile and package 
the source code, run the tests and install the resulting artifacts into your
local Maven repository (located in ~/.m2/repository/). The build artifacts can
be found in the target directories of each project (examples/target/,
examples/chapter8/target/). 


Asking Questions/Getting Help
-----------------------------
If you have questions about the ActiveMQ In Action book, please post a message
to the Manning book forum located here: 

  http://www.manning-sandbox.com/forum.jspa?forumID=496

If you have question about the ActiveMQ software, please post a message to the
ActiveMQ user mailing list. Information on subscribing to the mailing list or
the Nabble forum are available here: 

  http://activemq.apache.org/mailing-lists.html


Accessing the Source Code
-------------------------
This source code is directly accessible from the Google Code project's
Subversion source control repository available here: 

  http://code.google.com/p/activemq-in-action/


Colophon 
--------
The ActiveMQ In Action book was authored using DocBook XML and was processed
using the Docbkx Tools Maven plug-in on Mac OS X. Other items that went into
the book include MacBook Pros, Google Docs, GMail, Foonz (until it shut down),
FreeConferenceCall.com, barking dogs during conference calls, company
acquisitions, lots and lots of music, loud construction next door, sleepless
nights, too much work on airplanes, and plain old exhaustion.

We hope you enjoy it! 

Sincerely, 
    Dejan, Rob and Bruce 
