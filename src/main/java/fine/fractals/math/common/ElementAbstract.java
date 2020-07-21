package fine.fractals.math.common;

import fine.fractals.color.things.ScreenColor;
import fine.fractals.data.objects.State;

import java.awt.*;

public abstract class ElementAbstract {

	protected Color color;
	protected int lastIteration;
	protected Integer value;
	protected Integer valueCorrected;
	protected State state = State.ActiveNew;

	public ElementAbstract() {
		this.lastIteration = 0;
		this.valueCorrected = 0;
		this.value = 0;
	}

	public boolean isHibernatedBlack() {
		return this.state == State.HibernatedBlack;
	}

	public void setHibernatedBlack() {
		this.state = State.HibernatedBlack;
		this.value = 0;
		this.valueCorrected = 0;
	}

	public void setHibernatedBlackNeighbour() {
		if (this.state != State.HibernatedBlack) {
			this.state = State.HibernatedBlackNeighbour;
			this.value = 0;
			this.valueCorrected = 0;
		}
	}

	public void resetForOptimization() {
		this.state = State.ActiveNew;
		this.color = null;
	}

	public boolean isHibernatedBlack_Neighbour() {
		return this.state == State.HibernatedBlackNeighbour;
	}

	public boolean isHibernatedFinished() {
		return this.state == State.HibernatedFinished;
	}

	/* Inside means that path of this divergent element had enough hits inside displayed area image */
	public boolean isHibernatedFinishedInside() {
		return this.state == State.HibernatedFinishedInside;
	}

	public int getLastIteration() {
		return this.lastIteration;
	}

	public void setLastIteration(int lastIteration) {
		this.lastIteration = lastIteration;
	}

	/* set both real and correct value */
	public void setValues(int value) {
		this.value = value;
		this.valueCorrected = value;
	}

	public void setValueCorrect(int value) {
		this.valueCorrected = value;
	}

	public Integer getValue() {
		return this.value;
	}

	public Integer getValueCorrected() {
		return this.valueCorrected;
	}

	public void setBlack() {
		this.value = 0;
		this.valueCorrected = 0;
	}

	public void setHibernatedFinished() {
		this.state = State.HibernatedFinished;
	}

	public void setHibernatedFinishedInside() {
		this.state = State.HibernatedFinishedInside;
	}

	public void setActiveMoved() {
		if (this.state == State.ActiveNew) {
			this.state = State.ActiveMoved;
		}
	}

	public void setActiveRecalculate() {
		if (this.state != State.HibernatedFinishedInside) {
			this.state = State.ActiveRecalculate;
			this.color = ScreenColor._RECALCULATE;
		}
	}

	/* This should be called only for deep black on Optimization fix */
	public void setActiveFixed() {
		this.state = State.ActiveFixed;
	}

	public boolean isFixed() {
		return this.state == State.ActiveFixed;
	}

	public boolean isActiveNew() {
		return this.state == State.ActiveNew;
	}

	public boolean isActiveRecalculate() {
		return this.state == State.ActiveRecalculate;
	}

	public boolean isActiveMoved() {
		return this.state == State.ActiveMoved;
	}

	public boolean isActiveAny() {

		return this.state == State.ActiveNew || this.state == State.ActiveMoved || this.state == State.ActiveFixed;

	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public State getState() {
		return this.state;
	}

}
