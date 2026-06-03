# hoffnung
> Dummy Provider app for original stock app  com.hoffnung

<br><br>
## Info
- Made for Qiratdahaf
- Stock apk https://github.com/transsion-graveyard/dump_tecno_cm6/blob/main/system_ext/app/Hoffnung/Hoffnung.apk
- Without hoffnung apk system doesn't boot properly  and gets stuck at Lockscreen with just showing Nav bar chip.

### Logcat error:
05-30 17:25:53.456  5927  5927 E ReliableDetectionController: java.lang.SecurityException: Failed to find provider com.hoffnung.cloudControl.RemoteConfigProvider for user 0; expected to find a valid ContentProvider for this authority
...

## To test dummy provider:
```bash
# content query --uri content://com.hoffnung.cloudControl.RemoteConfigProvider/config                                                                         
Row: 0 key=os_tpms_rc_pkg_whitelist, value=com.android.shell,root
Row: 1 key=test_key, value=dummy
Row: 2 key=os_feature_b, value=false
Row: 3 key=os_feature_a, value=true
```

## License
hoffnung is licensed under the [GNU General Public License v3](http://www.gnu.org/copyleft/gpl.html).