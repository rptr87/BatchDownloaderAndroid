package app.batchimagedownloader;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by puttaraju on 16-05-2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private final SparseArray<Group> groups;
	public LayoutInflater inflater;
	public Activity activity;


	public ExpandableListAdapter(Activity act, SparseArray<Group> groups) {
		activity = act;
		this.groups = groups;
		inflater = act.getLayoutInflater();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).children.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
	                         boolean isLastChild, View convertView, ViewGroup parent) {
		final String children = (String) getChild(groupPosition, childPosition);
		TextView text = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_layout, null);
		}
		text = (TextView) convertView.findViewById(R.id.textView1);
		text.setText(children);
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(activity, children, Toast.LENGTH_SHORT).show();

				EditText filepath = (EditText) activity.findViewById(R.id.path_edit_text);
				String filepathstr = filepath.getEditableText().toString();

				File directory = new File(filepathstr);
				if(directory.exists()) {
					Dialog settingsDialog = new Dialog(activity);
					settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
					ImageView image = new ImageView(activity);
					image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
					image.setImageBitmap(BitmapFactory.decodeFile(directory.getPath()+"/"+children));
					settingsDialog.setContentView(image);
					settingsDialog.show();
				}

			}
		});
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).children.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
	                         View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listrow_group, null);
		}
		Group group = (Group) getGroup(groupPosition);
		((CheckedTextView) convertView).setText(group.string);
		((CheckedTextView) convertView).setChecked(isExpanded);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}