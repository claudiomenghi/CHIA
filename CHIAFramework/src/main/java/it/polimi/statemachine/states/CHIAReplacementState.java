package it.polimi.statemachine.states;

import it.polimi.action.CHIAAction;
import it.polimi.action.CHIAException;
import it.polimi.constraints.components.RefinementGenerator;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.replacement.ReplacementToStringTransformer;
import it.polimi.replacementchecker.ReplacementChecker;

/**
 * represents a possible state of CHIA. It offers a method perform that given
 * the current state and a <code>CHIAAction</code> returns the next state of the
 * automaton.
 * 
 * @author Claudio Menghi
 */
public enum CHIAReplacementState implements CHIAStateInterface {

    /**
     * is the initial state of the automaton
     */
    INIT {
        @Override
        public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction) {
            if (chiaAction == ReplacementReader.class) {
                return true;
            }
            if (chiaAction == ConstraintReader.class) {
                return true;
            }
            return false;
        }

        @Override
        public CHIAReplacementState perform(
                Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {

            if (chiaAction == ReplacementReader.class) {
                return REPLACEMENTLOADED;
            }
            if (chiaAction == ConstraintReader.class) {
                return CONSTRAINTLOADED;
            }

            throw new CHIAException("You cannot perform the action: "
                    + chiaAction.getName() + " into the state "
                    + this.toString());

        }
    },
    /**
     * is a state where the replacement is loaded but the subproperty is not
     */
    REPLACEMENTLOADED {
        @Override
        public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction) {
            if (chiaAction == ReplacementReader.class) {
                return true;
            }
            if (chiaAction == ConstraintReader.class) {
                return true;
            }
            if (chiaAction == ReplacementToStringTransformer.class) {
                return true;
            }
            if (chiaAction == RefinementGenerator.class) {
                return true;
            }
            return false;
        }

        @Override
        public CHIAReplacementState perform(
                Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {

            if (chiaAction == ReplacementReader.class) {
                return REPLACEMENTLOADED;
            }
            if (chiaAction == ConstraintReader.class) {
                return READY;
            }
            if (chiaAction == ReplacementToStringTransformer.class) {
                return REPLACEMENTLOADED;
            }
            if (chiaAction == RefinementGenerator.class) {
                return REPLACEMENTLOADED;
            }

            throw new CHIAException("You cannot perform the action: "
                    + chiaAction.getName() + " into the state "
                    + this.toString());
        }
    },
    CONSTRAINTLOADED {
        @Override
        public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction) {
            if (chiaAction == ConstraintReader.class) {
                return true;
            }
            if (chiaAction == ReplacementReader.class) {
                return true;
            }
            if (chiaAction == ConstraintToStringTrasformer.class) {
                return true;
            }
            return false;
        }

        @Override
        public CHIAReplacementState perform(
                Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {

            if (chiaAction == ConstraintReader.class) {
                return CONSTRAINTLOADED;
            }
            if (chiaAction == ReplacementReader.class) {
                return READY;
            }
            if (chiaAction == ConstraintToStringTrasformer.class) {
                return CONSTRAINTLOADED;
            }

            throw new CHIAException("You cannot perform the action: "
                    + chiaAction.getName() + " into the state "
                    + this.toString());
        }
    },

    READY {
        @Override
        public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction) {
            if (chiaAction == ConstraintToStringTrasformer.class) {
                return true;
            }
            if (chiaAction == ReplacementToStringTransformer.class) {
                return true;
            }
            if (chiaAction == ReplacementChecker.class) {
                return true;
            }
            if (chiaAction == ConstraintReader.class) {
                return true;
            }
            if (chiaAction == ReplacementReader.class) {
                return true;
            }
            if (chiaAction == RefinementGenerator.class) {
                return true;
            }

            return false;
        }

        @Override
        public CHIAReplacementState perform(
                Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {

            if (chiaAction == ConstraintToStringTrasformer.class) {
                return READY;
            }
            if (chiaAction == ReplacementToStringTransformer.class) {
                return READY;
            }
            if (chiaAction == ReplacementChecker.class) {
                return CHECKED;
            }
            if (chiaAction == ConstraintReader.class) {
                return READY;
            }
            if (chiaAction == ReplacementReader.class) {
                return READY;
            }
            if (chiaAction == RefinementGenerator.class) {
                return READY;
            }

            throw new CHIAException("You cannot perform the action: "
                    + chiaAction.getName() + " into the state "
                    + this.toString());
        }
    },
    CHECKED {
        @Override
        public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction) {
            if (chiaAction == ConstraintToStringTrasformer.class) {
                return true;
            }
            if (chiaAction == ReplacementToStringTransformer.class) {
                return true;
            }

            if (chiaAction == ConstraintReader.class) {
                return true;
            }
            if (chiaAction == ReplacementReader.class) {
                return true;
            }
            if (chiaAction == RefinementGenerator.class) {
                return true;
            }
            return false;
        }

        @Override
        public CHIAReplacementState perform(
                Class<? extends CHIAAction<?>> chiaAction) throws CHIAException {
            if (chiaAction == ConstraintToStringTrasformer.class) {
                return CHECKED;
            }
            if (chiaAction == ReplacementToStringTransformer.class) {
                return CHECKED;
            }

            if (chiaAction == ConstraintReader.class) {
                return READY;
            }
            if (chiaAction == ReplacementReader.class) {
                return READY;
            }
            if (chiaAction == RefinementGenerator.class) {
                return CHECKED;
            }
            throw new CHIAException("You cannot perform the action: "
                    + chiaAction.getName() + " into the state "
                    + this.toString());
        }
    };

    public abstract CHIAReplacementState perform(
            Class<? extends CHIAAction<?>> chiaAction) throws CHIAException;

}
