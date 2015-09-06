package com.example.complexlayout.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.complexlayout.utils.LocalDisplay;
import com.example.complexlayout.view.ListViewForScrollView;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

public class MainActivity extends AppCompatActivity {

    PtrClassicFrameLayout mPtrFrame;

    ArrayAdapter<String> mAdapter;

    ListViewForScrollView listView;

    private String[] adapterData = new String[]

            {
                    "Afghanistan", "Albania", "Algeria",
                    "American Samoa", "Andorra", "Angola", "Anguilla",
                    "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia",
                    "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain",
                    "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize",
                    "Benin", "Bermuda", "Bhutan", "Bolivia",
                    "Bosnia and Herzegovina", "Botswana", "Bouvet Island",
                    "Afghanistan", "Albania", "Algeria",
                    "American Samoa", "Andorra", "Angola", "Anguilla",
                    "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia",
                    "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain",
                    "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize",
                    "Benin", "Bermuda", "Bhutan", "Bolivia",
                    "Bosnia and Herzegovina", "Botswana", "Bouvet Island",
                    "Afghanistan", "Albania", "Algeria",
                    "American Samoa", "Andorra", "Angola", "Anguilla",
                    "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia",
                    "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain",
                    "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize",
                    "Benin", "Bermuda", "Bhutan", "Bolivia",
                    "Bosnia and Herzegovina", "Botswana", "Bouvet Island"
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);

        setHeader();

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        mPtrFrame.setDurationToClose(100);

        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 200);


        listView = (ListViewForScrollView) findViewById(R.id.rotate_header_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    final String url = mAdapter.getItem(position);
                    if (!TextUtils.isEmpty(url)) {
                        Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void setHeader() {
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrame);

        mPtrFrame.setLoadingMinTime(1000);
        mPtrFrame.setDurationToCloseHeader(1000);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void updateData() {
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.refreshComplete();

                if (mAdapter == null) {
                    mAdapter = new ArrayAdapter<String>(
                            MainActivity.this, android.R.layout.simple_list_item_1,
                            adapterData);

                    listView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }

            }
        }, 1500);
    }

}
