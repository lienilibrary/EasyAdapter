package com.lieni.library.easyadapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSelectionEasyAdapter<E> extends BaseEasyAdapter<E> {
    public static final int SELECTION_NONE = 0;
    public static final int SELECTION_TYPE_SINGLE = 1;
    public static final int SELECTION_TYPE_MULTIPLE = 2;
    protected List<Boolean> listSelections = new ArrayList<>();
    private int selectionType = SELECTION_TYPE_SINGLE;

    public BaseSelectionEasyAdapter() {
        this(0);
    }

    public BaseSelectionEasyAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void updateData(List<E> data) {
        resetSelections(data != null ? data.size() : 0);
        super.updateData(data);
    }

    @Override
    public void addData(E item, int position) {
        addSelection(position);
        super.addData(item, position);
    }

    @Override
    public void addData(List<E> data) {
        addSelections(data != null ? data.size() : 0);
        super.addData(data);
    }

    @Override
    public void removeItem(int position) {
        removeSelection(position);
        super.removeItem(position);
    }

    public boolean isSelectable() {
        return selectionType != SELECTION_NONE;
    }

    public boolean isPositionAvailable(int position) {
        return position < listSelections.size() && position > -1;
    }

    public void addSelection(int position) {
        if (isSelectable()) {
            if (position <= listSelections.size() && position > -1) {
                listSelections.add(position, false);
            }
        }
    }

    public void removeSelection(int position) {
        if (isSelectable()) {
            if (position < listSelections.size() && position > -1) {
                listSelections.remove(position);
            }
        }
    }

    public void resetSelections(int size) {
        if (isSelectable()) {
            listSelections.clear();
            for (int i = 0; i < size; i++) {
                listSelections.add(false);
            }
        }
    }

    public void addSelections(int additionalSize) {
        if (isSelectable()) {
            for (int i = 0; i < additionalSize; i++) {
                listSelections.add(false);
            }
        }
    }

    public void setSelectedPosition(int... positions) {
        if (isSelectable()) {
            if (isSingleSelection()) resetSelections(getListData().size());
            for (int position : positions) {
                if (isPositionAvailable(position)) {
                    listSelections.set(position, true);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void setSelectedItem(E item){
        int position=getListData().indexOf(item);
        if (isSelectable()&&isPositionAvailable(position)) {
            setSelectedPosition(position);
        }
    }

    public void removeSelectedPosition(int... positions) {
        if (isSelectable()) {
            for (int position : positions) {
                if (isPositionAvailable(position)) {
                    listSelections.set(position, false);
                    notifyItemChanged(position);
                }
            }
        }
    }

    public List<Integer> getSelectedPositions() {
        List<Integer> selectionPositions = new ArrayList<>();
        for (int i = 0; i < listSelections.size(); i++) {
            if (listSelections.get(i)) selectionPositions.add(i);
        }
        return selectionPositions;
    }

    public int getSelectedPosition() {
        List<Integer> selectionPositions = getSelectedPositions();
        return selectionPositions.size() > 0 ? selectionPositions.get(0) : -1;
    }

    public boolean isSelected(int position) {
        if (isSelectable() && isPositionAvailable(position)) {
            return listSelections.get(position);
        } else {
            return false;
        }
    }

    public boolean isSingleSelection() {
        return selectionType == SELECTION_TYPE_SINGLE;
    }

    public E getSelectedItem() {
        int position = getSelectedPosition();
        if (position < getListData().size() && position > -1)
            return getListData().get(getSelectedPosition());
        else return null;
    }

    public List<E> getSelectedItems() {
        List<E> selectedItems = new ArrayList<>();
        for (int i : getSelectedPositions()) {
            selectedItems.add(getListData().get(i));
        }
        return selectedItems;
    }

    public void enableSingleSelection(){
        this.selectionType=SELECTION_TYPE_SINGLE;
    }

    public void enableMultipleSelection(){
        this.selectionType=SELECTION_TYPE_MULTIPLE;
    }

    public void disableSelection(){
        this.selectionType=SELECTION_NONE;
    }

}
