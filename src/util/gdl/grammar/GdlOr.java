package util.gdl.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public final class GdlOr extends GdlLiteral
{

	private final List<GdlLiteral> disjuncts;
	private transient Boolean ground;

	GdlOr(List<GdlLiteral> disjuncts)
	{
		this.disjuncts = disjuncts;
		ground = null;
	}

	public int arity()
	{
		return disjuncts.size();
	}

	private boolean computeGround()
	{
		for (GdlLiteral literal : disjuncts)
		{
			if (!literal.isGround())
			{
				return false;
			}
		}

		return true;
	}

	public GdlLiteral get(int index)
	{
		return disjuncts.get(index);
	}

	@Override
	public boolean isGround()
	{
		if (ground == null)
		{
			ground = computeGround();
		}

		return ground;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("( or ");
		for (GdlLiteral literal : disjuncts)
		{
			sb.append(literal + " ");
		}
		sb.append(")");

		return sb.toString();
	}

	@Override
	public GdlOr replace(Map<Gdl, Gdl> replacementMap) {

		List<GdlLiteral> newDisjuncts = new ArrayList<GdlLiteral>();
		boolean hasChanged = false;
		for (GdlLiteral literal : this.disjuncts) {
			Gdl newLiteral = Gdl.applyReplacement(literal, replacementMap);
			if (newLiteral != literal) hasChanged = true;
			newDisjuncts.add((GdlLiteral) newLiteral);
		}
		
		if (hasChanged) {
			return GdlPool.getOr(newDisjuncts);
		}
		
		return this;
	}

	@Override
	public List<Gdl> getChildren() {
		List<Gdl> result = new ArrayList<Gdl>();
		
		result.addAll(this.disjuncts);
		
		return result;
	}
}
