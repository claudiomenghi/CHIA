
alias echon="echo "


say "When the tool is executed the automata mode is firstly enabled"
say "To see all the possible commands it is sufficient to press TAB"

echo "\t\c"

say "by typing help and the name of the command, a short description of the command and its parameters is provided"

echon "help lm"
say "for example the command lm allows loading a model and requires to specify the path of the file in which the model is stored"
sleep 2
say "Let's assume that we are developing a sending message system. "
sleep 0.3
say "We may load our initial, incomplete and still to be refined design, using the command load model"
echon "lm \c"
say "in this specific case our model is stored in the Model dot xml file which is located in the CHIA examples"
echon "CHIAExamples/\c"
sleep 0.3
say "sending message"
echon "SENDINGMESSAGE/\c"
sleep 0.3
say "automata folder"
echon "Aut/\c"
sleep 0.3
echon "Model.xml"
sleep 2
say "We can see the model which is corrently considered by chia using the command display model"
echo "dispm"
say "In our example the considered model has one initial state, the state q one, the black box states send one and send two, and the accepting states q two and q three"
sleep 0.5
say "The state q one is the initial state of the system, that is the state from which the computation starts"
sleep 0.5
say "The states send one and send two are black box states. They represent two functionalities that are in charge of sending a message"
say "At the current development stage we still have to design the send one and send two components"
say "It could be that inside send one the system tries to send a message using a wifi connection, while in send two a fibre connection is used"
sleep 0.5
say "Finally, the states q two and q three represent the failure and the success state, respectively"
sleep 0.5
say "The transition relation specified how the state of the system changes over time."
say "For example, when the transition one is fired, that means that a start input is received, the system modes into the state send one"
sleep 0.5
sleep 1
say "As a developer we may want to know whether the model possesses some of the properties of interest."
say "Let's consider for example the case in which we would like to check if, after a message is sent, it is finally delivered"
echon "lpLTL \c"
sleep 2
say "We can use the command lpLTL and then specify the property of interest"
echon "[](send-><>success)"
say "Is the property satisfied?"
sleep 0.7
say "Is it not satisfied?"
sleep 0.7
say "is it possibly satisfied?"
sleep 0.7
say "As a developer we do not want to start another development round, if the property is not satisfied."
say "Our time is important."
say "We want to make sure tha before refining send one or send two the property is satisfied or possibly satisfied"
sleep 1
say "For this reason we ask CHIA to check if the property holds in our initial design"
say "by running the command check, CHIA provides us the desired answer"
echon "ck"
say "In particular, in this case, the property is possibly satisfied"
sleep 1
say "By exploiting the constraint computation method we are able to precompute a set of subproperties for the black box states"
sleep 0.5
say "The subproperties that can be used when a replacement is designed to reduce the verification effort"
echon "cc"
say "The constraint is saved through the command save constraint"
echon "sc CHIAExamples/SENDINGMESSAGE/Rep/LivenessConstraint.xml"

say "At this point we start the first refinement round"
say "We first enter the replacement mode"
echon "rep"
say "Then the developer desings the replacement for the black box state send one"
say "The design can be loaded through the command load replacement"
echon "lr CHIAExamples/SENDINGMESSAGE/Rep/ReplacementSend1.xml"
say "It is possible to show the replacement through the command display replacement"
echon "dispr"

say "The incoming transition connect the state q one of the model to the state q four of the replacement"
sleep 0.5
say "The automaton contained inside the incomplete buchi automaton xml element describes the behavior of the system inside the black box state send one"
sleep 0.5
say "Finally, the outgoing transitions specify how the replacement can be left"
sleep "1"
say "To check whether the new design satisfy the original property it is possible to verify the replacement versus the previously computed constraint" 
say "For this reason, the constraint is loaded through the command load constraint"
echon "lc CHIAExamples/SENDINGMESSAGE/Rep/LivenessConstraint.xml"

say "And the verification is performed througth the command check"
echon "ck"

say "The property remains possibly satisfied, since its satisfaction depends on the replacement of the state send two"

say "Note that the verification time is around ten milliseconds, and the size of the automata generated is fourtyseven"
exit 
exit
