package assignment;

public abstract class Frontier {
	public abstract Node pop();

	public abstract boolean is_empty();

	public abstract boolean push(Node node);
}