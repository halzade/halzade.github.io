package fine.fractals.data.objects;

public enum State {
	ActiveNew,
	ActiveMoved,
	ActiveFixed,
	ActiveRecalculate,

	HibernatedFinished,
	HibernatedFinishedInside,
	HibernatedBlack,
	HibernatedBlackNeighbour,

}
