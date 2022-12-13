package models;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.AbstractListModel;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class FigureListModel extends AbstractListModel<FigureModel> implements Iterable<FigureModel> {

	private final ArrayList<FigureModel> data;

	public FigureListModel() {
		super();
		data = new ArrayList<>();
	}

	public void unselectAll() {

		for (var model : this) {
			model.setSelected(false);
		}

	}

	public boolean addElement(FigureModel figure) {

		int index = indexOf(figure);

		if (index != -1) {
			setElementAt(index, figure);
			return true;
		}

		index = getSize();
		boolean completed = data.add(figure);

		if (completed) {
			fireIntervalAdded(this, index, index);
		}

		return completed;
	}

	public boolean removeElement(FigureModel figure) {

		int index = indexOf(figure);

		boolean completed = data.remove(figure);

		if (index != -1) {
			fireIntervalRemoved(this, index, index);
		}

		return completed;
	}

	public FigureModel setElementAt(int index, FigureModel figure) {

		FigureModel old = data.set(index, figure);
		fireContentsChanged(this, index, index);

		return old;
	}

	public FigureModel removeElementAt(int index) {

		FigureModel old = data.remove(index);
		fireIntervalRemoved(this, index, index);

		return old;
	}

	public void clear() {

		int endIndex = getSize() - 1;
		data.clear();

		if (endIndex != -1) {
			fireIntervalRemoved(this, 0, endIndex);
		}

	}

	public boolean isEmpty() {
		return data.isEmpty();
	}

	public boolean contains(FigureModel figure) {

		for (FigureModel it : this) {

			if (it.getId() == figure.getId()) {
				return true;
			}

		}

		return false;
	}

	public int indexOf(FigureModel figure) {
		if (!contains(figure)) {
			return -1;
		}

		for (int i = 0; i < data.size(); i++) {

			if (data.get(i).getId() == figure.getId()) {
				return i;
			}

		}

		return -1;
	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public FigureModel getElementAt(int index) {
		return data.get(index);
	}

	@Override
	public Iterator<FigureModel> iterator() {
		return data.iterator();
	}

	public FigureModel[] toArray() {
		return (FigureModel[]) data.toArray();
	}

}
