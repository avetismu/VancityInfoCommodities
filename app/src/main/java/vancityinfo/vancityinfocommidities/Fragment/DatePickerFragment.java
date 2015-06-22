package vancityinfo.vancityinfocommidities.Fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private DatePickerListener mListener;
    private GregorianCalendar mCalendar;
    private String mTag;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        gregorianCalendar.set(
                year,
                month,
                day
        );
        mListener.setPickedDate(gregorianCalendar, mTag);
    }

    public void setListener(DatePickerListener listener){
        mListener = listener;
    }

    public void setCalendar(GregorianCalendar mCalendar) {
        this.mCalendar = mCalendar;
    }

    public void setTag(String tag){ mTag = tag;}

    public interface DatePickerListener{

        void setPickedDate(GregorianCalendar calendar, String tag);

    }
}
