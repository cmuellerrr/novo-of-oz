novo-of-oz
==========

The computer portion of Team Novo's mid-fidelity prototype.  It sends commands to a head-mounted display to display the necessary wireframes.


## Background

This is a prototyping tool developed by [Team Novo's](http://www.teamnovo.com) in order to quickly prototype for a head-mounted display.  
Not a lot of prototyping tools exist for HMDs and, in order to get the speed and flexibility of paper prototyping, we created our own system.

This uses the wizard-of-oz method for mimicking voice communication with a HMD application.

More information on our process can be found on [our project website](http://www.teamnovo.com).


## Specification


### Description

Our medium fidelity prototype utilizes communcation between a computer and HMD in order to display application wireframes.  
A human responds to voice commands from a user by sending messages from their computer to the HMD being worn by the user.
The computer acts as the brain, while the HMD merely shows what it is told (Get it? Wizard of Oz. The scarecrow. No brain.).

The HMD protion of the prototype can be found at its [github repo](http://github.com/cmuellerrr/scarecrow).


### Dependencies

novo-of-oz is an application written in JAVA. It requires the following libraries:

* [Processing 2.0](http://processing.org)
* [json4processing] (https://github.com/agoransson/JSON-processing)
* [minim 2.0.2] (http://code.compartmental.net/tools/minim/) This is one of processing's included libraries but you still have to link it.


## Other

Our final protoype can be found at its respective github repos:

* [PEER](http://www.github.com/cmuellerrr/PEER)
* [ARbra](http://www.github.com/gordonsliu/ARbra)
