package com.devking.android.frame1.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import com.devking.android.frame1.app.util.ViewHelper;

import java.util.List;
import java.util.Map;

public class QuickExpandableListAdapter extends BaseExpandableListAdapter {
	private List<? extends Map<String, ?>> mGroupData;
	private int mExpandedGroupLayout;
	private int mCollapsedGroupLayout;
	private String[] mGroupFrom;
	private int[] mGroupTo;

	private List<? extends List<? extends Map<String, ?>>> mChildData;
	private int mChildLayout;
	private int mLastChildLayout;
	private String[] mChildFrom;
	private int[] mChildTo;

	private GroupViewBinder mGroupBinder;
	private ChildViewBinder mChildBinder;
	private LayoutInflater mInflater;

	/**
	 * Constructor
	 * 
	 * @param context
	 *            The context where the
	 *            {@link android.widget.ExpandableListView} associated with this
	 *            {@link android.widget.SimpleExpandableListAdapter} is running
	 * @param groupData
	 *            A List of Maps. Each entry in the List corresponds to one
	 *            group in the list. The Maps contain the data for each group,
	 *            and should include all the entries specified in "groupFrom"
	 * @param groupFrom
	 *            A list of keys that will be fetched from the Map associated
	 *            with each group.
	 * @param groupTo
	 *            The group views that should display column in the "groupFrom"
	 *            parameter. These should all be TextViews. The first N views in
	 *            this list are given the values of the first N columns in the
	 *            groupFrom parameter.
	 * @param groupLayout
	 *            resource identifier of a view layout that defines the views
	 *            for a group. The layout file should include at least those
	 *            named views defined in "groupTo"
	 * @param childData
	 *            A List of List of Maps. Each entry in the outer List
	 *            corresponds to a group (index by group position), each entry
	 *            in the inner List corresponds to a child within the group
	 *            (index by child position), and the Map corresponds to the data
	 *            for a child (index by values in the childFrom array). The Map
	 *            contains the data for each child, and should include all the
	 *            entries specified in "childFrom"
	 * @param childFrom
	 *            A list of keys that will be fetched from the Map associated
	 *            with each child.
	 * @param childTo
	 *            The child views that should display column in the "childFrom"
	 *            parameter. These should all be TextViews. The first N views in
	 *            this list are given the values of the first N columns in the
	 *            childFrom parameter.
	 * @param childLayout
	 *            resource identifier of a view layout that defines the views
	 *            for a child. The layout file should include at least those
	 *            named views defined in "childTo"
	 */
	public QuickExpandableListAdapter(Context context,
			List<? extends Map<String, ?>> groupData, int groupLayout,
			String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, String[] childFrom, int[] childTo) {
		this(context, groupData, groupLayout, groupLayout, groupFrom, groupTo,
				childData, childLayout, childLayout, childFrom, childTo);
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 *            The context where the
	 *            {@link android.widget.ExpandableListView} associated with this
	 *            {@link android.widget.SimpleExpandableListAdapter} is running
	 * @param groupData
	 *            A List of Maps. Each entry in the List corresponds to one
	 *            group in the list. The Maps contain the data for each group,
	 *            and should include all the entries specified in "groupFrom"
	 * @param groupFrom
	 *            A list of keys that will be fetched from the Map associated
	 *            with each group.
	 * @param groupTo
	 *            The group views that should display column in the "groupFrom"
	 *            parameter. These should all be TextViews. The first N views in
	 *            this list are given the values of the first N columns in the
	 *            groupFrom parameter.
	 * @param expandedGroupLayout
	 *            resource identifier of a view layout that defines the views
	 *            for an expanded group. The layout file should include at least
	 *            those named views defined in "groupTo"
	 * @param collapsedGroupLayout
	 *            resource identifier of a view layout that defines the views
	 *            for a collapsed group. The layout file should include at least
	 *            those named views defined in "groupTo"
	 * @param childData
	 *            A List of List of Maps. Each entry in the outer List
	 *            corresponds to a group (index by group position), each entry
	 *            in the inner List corresponds to a child within the group
	 *            (index by child position), and the Map corresponds to the data
	 *            for a child (index by values in the childFrom array). The Map
	 *            contains the data for each child, and should include all the
	 *            entries specified in "childFrom"
	 * @param childFrom
	 *            A list of keys that will be fetched from the Map associated
	 *            with each child.
	 * @param childTo
	 *            The child views that should display column in the "childFrom"
	 *            parameter. These should all be TextViews. The first N views in
	 *            this list are given the values of the first N columns in the
	 *            childFrom parameter.
	 * @param childLayout
	 *            resource identifier of a view layout that defines the views
	 *            for a child. The layout file should include at least those
	 *            named views defined in "childTo"
	 */
	public QuickExpandableListAdapter(Context context,
			List<? extends Map<String, ?>> groupData, int expandedGroupLayout,
			int collapsedGroupLayout, String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, String[] childFrom, int[] childTo) {
		this(context, groupData, expandedGroupLayout, collapsedGroupLayout,
				groupFrom, groupTo, childData, childLayout, childLayout,
				childFrom, childTo);
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 *            The context where the
	 *            {@link android.widget.ExpandableListView} associated with this
	 *            {@link android.widget.SimpleExpandableListAdapter} is running
	 * @param groupData
	 *            A List of Maps. Each entry in the List corresponds to one
	 *            group in the list. The Maps contain the data for each group,
	 *            and should include all the entries specified in "groupFrom"
	 * @param groupFrom
	 *            A list of keys that will be fetched from the Map associated
	 *            with each group.
	 * @param groupTo
	 *            The group views that should display column in the "groupFrom"
	 *            parameter. These should all be TextViews. The first N views in
	 *            this list are given the values of the first N columns in the
	 *            groupFrom parameter.
	 * @param expandedGroupLayout
	 *            resource identifier of a view layout that defines the views
	 *            for an expanded group. The layout file should include at least
	 *            those named views defined in "groupTo"
	 * @param collapsedGroupLayout
	 *            resource identifier of a view layout that defines the views
	 *            for a collapsed group. The layout file should include at least
	 *            those named views defined in "groupTo"
	 * @param childData
	 *            A List of List of Maps. Each entry in the outer List
	 *            corresponds to a group (index by group position), each entry
	 *            in the inner List corresponds to a child within the group
	 *            (index by child position), and the Map corresponds to the data
	 *            for a child (index by values in the childFrom array). The Map
	 *            contains the data for each child, and should include all the
	 *            entries specified in "childFrom"
	 * @param childFrom
	 *            A list of keys that will be fetched from the Map associated
	 *            with each child.
	 * @param childTo
	 *            The child views that should display column in the "childFrom"
	 *            parameter. These should all be TextViews. The first N views in
	 *            this list are given the values of the first N columns in the
	 *            childFrom parameter.
	 * @param childLayout
	 *            resource identifier of a view layout that defines the views
	 *            for a child (unless it is the last child within a group, in
	 *            which case the lastChildLayout is used). The layout file
	 *            should include at least those named views defined in "childTo"
	 * @param lastChildLayout
	 *            resource identifier of a view layout that defines the views
	 *            for the last child within each group. The layout file should
	 *            include at least those named views defined in "childTo"
	 */
	public QuickExpandableListAdapter(Context context,
			List<? extends Map<String, ?>> groupData, int expandedGroupLayout,
			int collapsedGroupLayout, String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, int lastChildLayout, String[] childFrom,
			int[] childTo) {
		mGroupData = groupData;
		mExpandedGroupLayout = expandedGroupLayout;
		mCollapsedGroupLayout = collapsedGroupLayout;
		mGroupFrom = groupFrom;
		mGroupTo = groupTo;

		mChildData = childData;
		mChildLayout = childLayout;
		mLastChildLayout = lastChildLayout;
		mChildFrom = childFrom;
		mChildTo = childTo;

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public Object getChild(int groupPosition, int childPosition) {
		return mChildData.get(groupPosition).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v;
		if (convertView == null) {
			v = newChildView(isLastChild, parent);
		} else {
			v = convertView;
		}
		// 更改
		bindChildView(v, mChildData.get(groupPosition).get(childPosition),
				mChildFrom, mChildTo, groupPosition, childPosition);
		/*
		 * bindView(v, mChildData.get(groupPosition).get(childPosition),
		 * mChildFrom, mChildTo);
		 */
		return v;
	}

	/**
	 * Instantiates a new View for a child.
	 * 
	 * @param isLastChild
	 *            Whether the child is the last child within its group.
	 * @param parent
	 *            The eventual parent of this new View.
	 * @return A new child View
	 */
	public View newChildView(boolean isLastChild, ViewGroup parent) {
		return mInflater.inflate((isLastChild) ? mLastChildLayout
				: mChildLayout, parent, false);
	}

	/**
	 * 绑定View
	 * 
	 * @param view
	 * @param data
	 * @param from
	 * @param to
	 */
	/*
	 * private void bindView(View view, Map<String, ?> data, String[] from,
	 * int[] to) { int len = to.length;
	 * 
	 * for (int i = 0; i < len; i++) { View v = view.findViewById(to[i]); //
	 * 修改ViewBindingValue Tools.setViewValue(v, data.get(from[i]));
	 * 
	 * TextView v = (TextView) view.findViewById(to[i]); if (v != null) {
	 * v.setText((String) data.get(from[i])); }
	 * 
	 * } }
	 */

	/**
	 * 绑定GroupView的值
	 * 
	 * @param view
	 * @param data
	 * @param from
	 * @param to
	 */
	private void bindGroupView(View view, Map<String, ?> data, String[] from,
			int[] to, int position) {
		int len = to.length;
		if (mGroupBinder != null) {
			// 完成,循环调用
			for (int i = 0; i < len; i++) {
				boolean bound = false;
				View v = view.findViewById(to[i]);
				bound = mGroupBinder.setViewValue(v, data.get(from[i]),
						position);
				if (!bound) {
					ViewHelper.setViewValue(v, data.get(from[i]));
				}
			}
		} else {
			for (int i = 0; i < len; i++) {
				View v = view.findViewById(to[i]);
				ViewHelper.setViewValue(v, data.get(from[i]));
			}
		}

	}

	/**
	 * 绑定ChildView的值
	 * 
	 * @param view
	 * @param data
	 * @param from
	 * @param to
	 */
	private void bindChildView(View view, Map<String, ?> data, String[] from,
			int[] to, int parentPosition, int childPosition) {
		int len = to.length;
		if (mChildBinder != null) {
			// 完成,循环调用
			for (int i = 0; i < len; i++) {
				boolean bound = false;
				View v = view.findViewById(to[i]);
				bound = mChildBinder.setViewValue(v, data.get(from[i]),
						parentPosition, childPosition);
				if (!bound) {
					ViewHelper.setViewValue(v, data.get(from[i]));
				}
			}
		} else {
			for (int i = 0; i < len; i++) {
				View v = view.findViewById(to[i]);
				ViewHelper.setViewValue(v, data.get(from[i]));
			}
		}
	}

	public int getChildrenCount(int groupPosition) {
		return mChildData.get(groupPosition).size();
	}

	public Object getGroup(int groupPosition) {
		return mGroupData.get(groupPosition);
	}

	public int getGroupCount() {
		return mGroupData.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v;
		if (convertView == null) {
			v = newGroupView(isExpanded, parent);
		} else {
			v = convertView;
		}
		// 更改
		bindGroupView(v, mGroupData.get(groupPosition), mGroupFrom, mGroupTo,
				groupPosition);
		/* bindView(v, mGroupData.get(groupPosition), mGroupFrom, mGroupTo); */
		return v;
	}

	/**
	 * Instantiates a new View for a group.
	 * 
	 * @param isExpanded
	 *            Whether the group is currently expanded.
	 * @param parent
	 *            The eventual parent of this new View.
	 * @return A new group View
	 */
	public View newGroupView(boolean isExpanded, ViewGroup parent) {
		return mInflater.inflate((isExpanded) ? mExpandedGroupLayout
				: mCollapsedGroupLayout, parent, false);
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

	public void setGroupViewBinder(GroupViewBinder binder) {
		mGroupBinder = binder;
	}

	public void setChildViewBinder(ChildViewBinder binder) {
		mChildBinder = binder;
	}

	/**
	 * 绑定GroupView值
	 * 
	 * @author Yu
	 * @date 2013-11-14
	 */
	public interface GroupViewBinder {
		boolean setViewValue(View view, Object data, int position);
	}

	/**
	 * 绑定ChildView的值
	 * 
	 * @author Yu
	 * @date 2013-11-14
	 */
	public interface ChildViewBinder {
		boolean setViewValue(View view, Object data, int parentPosition,
							 int childPosition);
	}
}
