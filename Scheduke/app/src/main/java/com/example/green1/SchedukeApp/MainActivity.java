

package com.example.green1.SchedukeApp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView mAllClasses;
    private ListView mClassPageList;
    private CustomArrayAdapter mArrayAdapter;
    private CustomArrayAdapter mArrayAdapter2;

    private ViewAnimator MainView;
    private LinearLayout allClasses;
    private RelativeLayout classPage;
    private LinearLayout AddClassPage;
    private LinearLayout AddAssignmentPage;
    private LinearLayout infoPage;

    private static Button mAButton;
    public static final int NOTIFICATION_ID = 3967;
    private static Button mUnusedButton;
    public static Context MainContext;
    private static NotificationCompat.Builder builder;
    private static String classNotifyString;
    private static Schedule mainSchedule;

    private static Button addClassButton;
    private static Button cancelClassButton;
    private static Button okAddAssignmentButton;
    private static Button okAssignmentButton;
    private static Button cancelAddAssignmentButton;
    private static Button CancelAssignmentButton;
    public static EditText inputText;
    private static NotificationManager notificationManager;
    public static Resources res;
    private static CharSequence selectedClassName;
    private static String timeNotifyString;
     private int inputPeriod;
    private EditText AssignmentName;
    private EditText AssignmentDay;
    private EditText AssignmentMonth;
    private EditText AssignmentYear;

    private EditText AMPMTextEdit;
    private EditText periodTextEdit;
    private EditText startMinTextEdit;
    private EditText startHourTextEdit;
    private EditText classNameTextEdit;
    private EditText assigmentInfoName;
    private EditText assignmentInfoPeriod;
    private EditText assignmentInfoMonth;
    private EditText assignmentInfoDay;
    private EditText assignmentInfoYear;
    private NotificationManager mNotificationManager;
    private TextView selectedClassTextView;
    private ArrayList<String> mArray= new ArrayList<String>();;
    private Class selectedClass;
    private ArrayList<String> mArraySub= new ArrayList<String>();
    private ArrayList<View> mPreviousViews = new ArrayList<>();
    private View temp;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
       if(mainSchedule == null)
            mainSchedule = new Schedule();
        MainContext = this;
        if(selectedClass == null)
            selectedClass = mainSchedule.getClassList().get(0);
        if(selectedClassName == null)
            selectedClassName = selectedClass.getName();
        setContentView(R.layout.activity_main);

        MainView = (ViewAnimator) findViewById(R.id.MainAnimator);
        allClasses =(LinearLayout) findViewById(R.id.ListViewLayoutPage);
        classPage = (RelativeLayout) findViewById(R.id.ClassPage);
        AddClassPage = (LinearLayout) findViewById(R.id.ClassAddScreen);
        AddAssignmentPage = (LinearLayout) findViewById(R.id.AddAssignemntScreen);
        infoPage = (LinearLayout) findViewById(R.id.assignmentInfo);
        AssignmentName = (EditText)findViewById(R.id.AssignmentName);
        AssignmentDay = (EditText)findViewById(R.id.AddAssignmentDay);
        AssignmentMonth = (EditText)findViewById(R.id.AddAssignmentMonth);
        AssignmentYear = (EditText)findViewById(R.id.AddAssignmentYear);
        AMPMTextEdit = (EditText)findViewById(R.id.AddTimeFrame);
        periodTextEdit = (EditText)findViewById(R.id.AddPeriod);
        startMinTextEdit = (EditText)findViewById(R.id.AddMin);
        startHourTextEdit = (EditText)findViewById(R.id.AddHour);
        classNameTextEdit = (EditText)findViewById(R.id.AddedClassName);

        assigmentInfoName = (EditText)findViewById(R.id.edClassName);
        assignmentInfoDay = (EditText)findViewById(R.id.Day);
        assignmentInfoMonth = (EditText)findViewById(R.id.Month);
        assignmentInfoYear = (EditText)findViewById(R.id.AssInfoYear);
        assignmentInfoPeriod = (EditText)findViewById(R.id.Period);


        makeMArray();
        makeMArraySub();
        updateClassView();

        mArrayAdapter = new CustomArrayAdapter(mArray, mArraySub, this);
        mArrayAdapter2 = new CustomArrayAdapter(selectedClass.getAssignmentNames(),selectedClass.getAssignmentDates() , MainContext);

        mAllClasses = (ListView) findViewById(R.id.LV);
        mAllClasses.setAdapter(mArrayAdapter);
        mClassPageList = (ListView) findViewById(R.id.ListView);
        mClassPageList.setAdapter(mArrayAdapter2);

        final FloatingActionButton FAB = (FloatingActionButton) findViewById(R.id.fab);
        FAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPreviousViews.add(MainView.getCurrentView());
                    switch (findDisplaychild(MainView.getCurrentView())) {
                        case 0:
                            MainView.setDisplayedChild(2);
                            break;
                        case 1:
                            MainView.setDisplayedChild(3);
                            break;
                    }
                }
            });
        if(TextUtils.isEmpty(periodTextEdit.getText())|| periodTextEdit.getText().toString().length() <3)
        {
            inputPeriod = 1;
        }
        else if(periodTextEdit.getText().toString().substring(0,1).equals("W")|| periodTextEdit.getText().toString().substring(0,1).equals("w"))
         {
            inputPeriod = 2*(Integer.valueOf(periodTextEdit.getText().toString().substring(1,2)));
        }
        else if(periodTextEdit.getText().toString().substring(0,1).equals("G")|| periodTextEdit.getText().toString().substring(0,1).equals("g"))
        {
            inputPeriod = Integer.valueOf(periodTextEdit.getText().toString().substring(1,2));
        }
        else
            inputPeriod = 1;


            addClassButton = (Button)findViewById(R.id.AddClassOKButton);
            addClassButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(TextUtils.isEmpty(classNameTextEdit.getText())|| TextUtils.isEmpty(startHourTextEdit.getText())|| TextUtils.isEmpty(startMinTextEdit.getText())|| TextUtils.isEmpty(periodTextEdit.getText()) || TextUtils.isEmpty(AMPMTextEdit.getText())))
                        mainSchedule.addToClassList(new Class(classNameTextEdit.getText().toString(), Integer.valueOf(startHourTextEdit.getText().toString()), Integer.valueOf(startMinTextEdit.getText().toString()), inputPeriod, AMPMTextEdit.getText().toString().toUpperCase()));

                    makeMArray();
                    makeMArraySub();
                    updateClassView();
                    MainView.setDisplayedChild(0);
                    FAB.setVisibility(View.VISIBLE);
                }
            });
            cancelClassButton = (Button)findViewById(R.id.AddClassCANCELButton);
            cancelClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainView.setDisplayedChild(0);
                FAB.setVisibility(View.VISIBLE);
            }
        });
        okAddAssignmentButton = (Button)findViewById(R.id.AddAssignmentOKButton);
        okAddAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TextUtils.isEmpty(AssignmentName.getText()) || TextUtils.isEmpty(AssignmentDay.getText()) || TextUtils.isEmpty(AssignmentMonth.getText()) || TextUtils.isEmpty(AssignmentYear.getText())))
                    selectedClass.addAssignment(AssignmentName.getText().toString(), Integer.valueOf(AssignmentDay.getText().toString()), Integer.valueOf(AssignmentMonth.getText().toString()), Integer.valueOf(AssignmentYear.getText().toString()));
                updateAssignmentView();
                MainView.setDisplayedChild(1);
                FAB.setVisibility(View.VISIBLE);
            }
        });
        cancelAddAssignmentButton = (Button)findViewById(R.id.AddAssignmentCancelButton);
        cancelAddAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        okAssignmentButton = (Button) findViewById(R.id.AssInfoOKButton);
        okAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mAllClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MainView.getCurrentView() == allClasses) {
                    screenSwitch();
                    selectedClassName = ((Class) mainSchedule.getClassList().get(position)).getName();
                    ((TextView) findViewById(R.id.ClassName)).setText(selectedClassName);
                    return;
                }
                MainView.setDisplayedChild(0);
            }
        });


        mClassPageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainView.setDisplayedChild(4);
                assignmentInfoYear.setText(selectedClass.getGradeList().get(position).getYear() + "");
                assignmentInfoMonth.setText(selectedClass.getGradeList().get(position).getMonth() + "");
                assignmentInfoDay.setText(selectedClass.getGradeList().get(position).getDay()+ "");
                assigmentInfoName.setText(selectedClass.getGradeList().get(position).getName());
                String tempPeriod = new String();
                if(selectedClass.getPeriod() > 4)
                {
                    tempPeriod = "W" + (selectedClass.getPeriod() -4);
                }
                else
                {
                    tempPeriod = "G" + (selectedClass.getPeriod()
                    );
                }
                assignmentInfoPeriod.setText(tempPeriod);
            }
        });


    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putStringArrayList("ClassArray", mArray);
        state.putStringArrayList("ClassArraySub", mArraySub);
        state.putParcelableArrayList("ClassList", mainSchedule.getClassList());
        state.putCharSequence("SelectedClassName", selectedClassName);
        state.putParcelable("SelectedClassObject", selectedClass);
        Log.v(TAG, "save");
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mArray = savedInstanceState.getStringArrayList("ClassArray");
        mArraySub = savedInstanceState.getStringArrayList("ClassArraySub");
        mainSchedule = new Schedule(savedInstanceState.getParcelableArrayList("ClassList"));
        selectedClassName = savedInstanceState.getCharSequence("SelectedClassName");
        selectedClass = savedInstanceState.getParcelable("SelectedClassObject");
        Log.v(TAG, "restore");
    }
    public static NotificationCompat.Builder getBuilder() {
        return builder;
    }

    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public NotificationManager getmNotificationManager() {
        return mNotificationManager;
    }

    public static void setBuilder(NotificationCompat.Builder paramBuilder) {
        builder = paramBuilder;
    }

    public int findDisplaychild(View v){
        if(v == allClasses){
            return 0;
        } else if (v == classPage){
            return 1;
        } else if (v == AddClassPage){
            return 2;
        }else if(v == infoPage){
            return 4;
        }else if (v == AddAssignmentPage){
            return 3;
        }else {
            return 0;
        }
    }

    public void screenSwitch(){
        View currentView = MainView.getCurrentView();
        mPreviousViews.add(MainView.getCurrentView());
        if(currentView == allClasses){
            MainView.setDisplayedChild(1);
        }else if(currentView == classPage){
            MainView.setDisplayedChild(3);
        }else if(currentView == infoPage){
            onBackPressed();
        }else if(currentView == AddAssignmentPage){
            onBackPressed();
        }else if(currentView == AddClassPage){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        Animation tempIn = MainView.getInAnimation();
        Animation tempOut = MainView.getOutAnimation();
        MainView.setInAnimation(this,R.anim.move_to_bottom_in);
        MainView.setOutAnimation(this,R.anim.move_to_bottom_out);
        if (mPreviousViews.size() > 0) {
            MainView.setDisplayedChild(findDisplaychild(mPreviousViews.remove(mPreviousViews.size() - 1)));
        }
        MainView.setInAnimation(tempIn);
        MainView.setOutAnimation(tempOut);
    }


    public static Button getmAButton() {
        return mAButton;
    }
    public void makeMArray() {
        if (mArray==null || mainSchedule.getClassList().size() != mArray.size()) {
            mArray = new ArrayList<String>();
            for (int i = 0; i < mainSchedule.getClassList().size(); i++) {
                mArray.add((String) mainSchedule.getClassList().get(i).getName());
            }
        }
    } public void makeMArraySub()
    {
        if (mArraySub == null || mainSchedule.getClassList().size() != mArraySub.size())
        {
            mArraySub = new ArrayList<String>();
            for (int i = 0; i < mainSchedule.getClassList().size(); i++)
            {
                if (mainSchedule.getClassList().get(i).getStartMins() > 9)
                    mArraySub.add((Integer.toString(((Class) mainSchedule.getClassList().get(i)).getStartHour()) + ":" + (Integer.toString(((Class) mainSchedule.getClassList().get(i)).getStartMins())))+" " + mainSchedule.getClassList().get(i).getAmPm().toUpperCase());
                else
                     mArraySub.add(Integer.toString(((Class) mainSchedule.getClassList().get(i)).getStartHour()) + ":0" + (Integer.toString(((Class) mainSchedule.getClassList().get(i)).getStartMins()))+" " + mainSchedule.getClassList().get(i).getAmPm().toUpperCase());
            }
        }
    }
    public void updateClassView()
    {
        mArrayAdapter = new CustomArrayAdapter(mArray, mArraySub, MainContext);
        mArrayAdapter.notifyDataSetChanged();
        mAllClasses = null;
        mAllClasses = (ListView) findViewById(R.id.LV);
        mAllClasses.setAdapter(mArrayAdapter);
    }
    public void updateAssignmentView()
    {
        mArrayAdapter2 = new CustomArrayAdapter(selectedClass.getAssignmentNames(),selectedClass.getAssignmentDates() , MainContext);
        mArrayAdapter2.notifyDataSetChanged();
        mClassPageList = null;
        mClassPageList = (ListView) findViewById(R.id.ListView);
        mClassPageList.setAdapter(mArrayAdapter2);
    }
}








