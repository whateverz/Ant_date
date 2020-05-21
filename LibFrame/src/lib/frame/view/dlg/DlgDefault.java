package lib.frame.view.dlg;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDButton;

import lib.frame.R;

public class DlgDefault extends MaterialDialog {

    DlgDefault(Builder builder) {
        super(builder);
        MDButton positiveButton = getActionButton(DialogAction.POSITIVE);
        MDButton negativeButton = getActionButton(DialogAction.NEGATIVE);
        MDButton neutralButton = getActionButton(DialogAction.NEUTRAL);
        setTypeface(positiveButton, builder.getRegularFont());
        setTypeface(negativeButton, builder.getRegularFont());
        setTypeface(neutralButton, builder.getRegularFont());
        setTypeface(title, builder.getRegularFont());

        negativeButton.setTextColor(builder.getContext().getResources().getColor(R.color.theme_color));
        positiveButton.setTextColor(builder.getContext().getResources().getColor(R.color.theme_color));
        neutralButton.setTextColor(builder.getContext().getResources().getColor(R.color.theme_color));
    }
}
