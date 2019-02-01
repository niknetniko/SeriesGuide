package com.battlelancer.seriesguide.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.battlelancer.seriesguide.R;
import com.battlelancer.seriesguide.ui.preferences.SgPreferencesFragment;

/**
 * Allows tweaking of various SeriesGuide settings. Does NOT inherit from {@link
 * com.battlelancer.seriesguide.ui.BaseActivity} to avoid handling actions which might be confusing
 * while adjusting settings.
 */
public class SeriesGuidePreferences extends AppCompatActivity {

    public static class UpdateSummariesEvent {
    }

    public static final String EXTRA_SETTINGS_SCREEN = "settingsScreen";

    public static @StyleRes int THEME = R.style.Theme_SeriesGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(SeriesGuidePreferences.THEME);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupActionBar();

        if (savedInstanceState == null) {
            Fragment f = new SgPreferencesFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.containerSettings, f);
            ft.commit();

            // open a sub settings screen if requested
            String settingsScreen = getIntent().getStringExtra(EXTRA_SETTINGS_SCREEN);
            if (settingsScreen != null) {
                switchToSettings(settingsScreen);
            }
        }
    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.sgToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        // Because we use the platform fragment manager we need to pop fragments on our own
        if (!getFragmentManager().popBackStackImmediate()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchToSettings(String settingsId) {
        Bundle args = new Bundle();
        args.putString(EXTRA_SETTINGS_SCREEN, settingsId);
        Fragment f = new SgPreferencesFragment();
        f.setArguments(args);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.containerSettings, f);
        ft.addToBackStack(null);
        ft.commit();
    }
}
