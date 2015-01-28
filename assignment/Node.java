package assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
	public int vertex_id;
	public int depth;
	public Node parent;
	public double heur;
	public Graph graph;

	public Node(int vertex_id, Graph graph) {
		this.vertex_id = vertex_id;
		depth = 0;
		parent = null;
		heur = 0;
		this.graph = graph;
	}

	public Node(int vertex_id, Graph graph, Node parent) {
		this.vertex_id = vertex_id;
		depth = 0;
		this.parent = parent;
		heur = 0;
		this.graph = graph;
	}

	public List<Node> successors() {
		List<Node> successors = new ArrayList<Node>();
		List<Edge> edges = graph.map_id_vertex.get(vertex_id).edges;
		for (Edge edge : edges) {
			int child_vertex_id = edge.vertex1_id != vertex_id ? edge.vertex1_id
					: edge.vertex2_id;

			if (parent != null && child_vertex_id == parent.vertex_id)
				continue;

			Node childNode = new Node(child_vertex_id, graph, this);
			childNode.depth = this.depth + 1;
			successors.add(childNode);
		}
		return successors;
	}

	public void calculate_heur(Vertex goal_vertex) {
		Vertex v = graph.map_id_vertex.get(vertex_id);
		heur = Math.sqrt(Math.pow((v.x - goal_vertex.x), 2)
				+ Math.pow((v.y - goal_vertex.y), 2));
	}

	public static class Searching {
		private Map<Integer, Boolean> visited;
		private int total_iter;
		private int max_frontier_size;
		private String search_type;

		private void initialize() {
			visited = new HashMap<Integer, Boolean>();
			total_iter = 0;
			max_frontier_size = 1;
			search_type = null;
		}

		public void traceback(Node node) {
			if (node == null)
				return;
			Node target = node;
			System.out.println("Solution path:");
			while (node != null) {
				Vertex v = node.graph.map_id_vertex.get(node.vertex_id);
				System.out.println("vertex " + node.vertex_id + " (" + v.x
						+ "," + v.y + ")");
				node = node.parent;
			}

			System.out.println("\nSearch algorithm:" + search_type);
			System.out.println("Total Iterations:" + total_iter);
			System.out.println("Maximum Frontier Size:" + max_frontier_size);
			System.out.println("Vertices Visited:" + visited.size() + "/"
					+ target.graph.vertexCount);
			System.out.println("Path Length:" + target.depth);
		}

		public Node Search(Node initial_state, Vertex goal_vertex,
				int frontier_value) {
			initialize();
			Frontier frontier = get_frontier(frontier_value, goal_vertex);
			frontier.push(initial_state);
			visited.put(initial_state.vertex_id, true);

			while (frontier.is_empty() != true) {
				Node front_node = frontier.pop();
				total_iter++;

				if (front_node.vertex_id == goal_vertex.id) {
					return front_node;
				}

				List<Node> successors = front_node.successors();
				for (Node successor : successors) {
					if (!visited.containsKey(successor.vertex_id)) {
						successor.calculate_heur(goal_vertex);
						frontier.push(successor);
						visited.put(successor.vertex_id, true);

						if (frontier.get_size() > max_frontier_size)
							max_frontier_size = frontier.get_size();
					}
				}
			}
			return null;
		}

		public Frontier get_frontier(int value, Vertex goal_vertex) {
			Frontier frontier = null;
			if (value == FrontierType.QUEUE.get_value()) {
				frontier = new MyQueue();
				search_type = FrontierType.QUEUE.to_string();
			} else if (value == FrontierType.STACK.get_value()) {
				frontier = new MyStack();
				search_type = FrontierType.STACK.to_string();
			} else {
				frontier = new MyPriorityQueue(goal_vertex);
				search_type = FrontierType.PRIORITY_QUEUE.to_string();
			}
			return frontier;
		}
	}
}
