package happy.happy3;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Heaven on 9/17/2016.
 */
public class chooseostanfragment extends Fragment {
	private RecyclerView mCrimeRecyclerView2;
	private CrimeAdapter2 mAdapter2;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.whichostan, container, false);


		mCrimeRecyclerView2 = (RecyclerView) v.findViewById(R.id.recycler_viewostan);
		mCrimeRecyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
		String[] tempstring2 = getResources().getStringArray(R.array.ostanha);
		List<String> tempstring3 = new ArrayList<String>(Arrays.asList(tempstring2));
		mAdapter2 = new CrimeAdapter2(tempstring3);
		mCrimeRecyclerView2.setAdapter(mAdapter2);
		return v;
	}
	private class CrimeHolder2 extends RecyclerView.ViewHolder
			implements View.OnClickListener  {
		public TextView mTitleTextView;
		public int position1;

		public CrimeHolder2(View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
			// mTitleTextView = (TextView) itemView;
			mTitleTextView=(TextView) itemView.findViewById(R.id.listtextview);
		}
		@Override
		public void onClick(View v) {
			QueryPreferences.setStoredInt(getActivity(), "ostan", position1);
			new MainFragment().ostan= position1;
			getActivity().getSupportFragmentManager().popBackStack();
		}
	}
	public class CrimeAdapter2 extends RecyclerView.Adapter<CrimeHolder2> {
		private List<String> mCrimes;
		public CrimeAdapter2(List<String> crimes) {
			mCrimes = crimes;
		}
		@Override
		public CrimeHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            /*
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
                    */
			View view = layoutInflater
					.inflate(R.layout.listtext, parent, false);
			return new CrimeHolder2(view);
		}
		@Override
		public void onBindViewHolder(CrimeHolder2 holder, int position) {
			String crime = mCrimes.get(position);
			holder.position1=position;
			holder.mTitleTextView.setText(crime);
		}
		@Override
		public int getItemCount() {
			return mCrimes.size();
		}
	}


}
