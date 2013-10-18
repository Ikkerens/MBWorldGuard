package com.ikkerens.worldguard.model;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.ikkerens.worldguard.Config;
import com.ikkerens.worldguard.model.flagtypes.GameModeFlag;
import com.ikkerens.worldguard.model.flagtypes.GroupStateFlag;
import com.ikkerens.worldguard.model.flagtypes.GroupStateFlag.GroupState;
import com.ikkerens.worldguard.model.flagtypes.StateFlag;
import com.ikkerens.worldguard.model.flagtypes.StateFlag.State;
import com.ikkerens.worldguard.model.flagtypes.StringArrayFlag;
import com.ikkerens.worldguard.model.flagtypes.StringFlag;

public abstract class Flags {
    public static final StateFlag               EXPLOSIONS   = new StateFlag( "explosions", State.ALLOW );
    public static final StateFlag               PVP          = new StateFlag( "pvp", State.ALLOW );
    public static final GroupStateFlag          BUILD        = new GroupStateFlag( "build", GroupState.MEMBERS );
    public static final GroupStateFlag          CHEST_ACCESS = new GroupStateFlag( "chest-access", GroupState.MEMBERS );
    public static final GroupStateFlag          INTERACT     = new GroupStateFlag( "interact", GroupState.ALLOW );
    public static final GroupStateFlag          ENTRY        = new GroupStateFlag( "entry", GroupState.ALLOW ) {
                                                                 @Override
                                                                 public boolean canUse( final Config config ) {
                                                                     return config.isUsingMove();
                                                                 }
                                                             };
    public static final GroupStateFlag          LEAVE        = new GroupStateFlag( "leave", GroupState.ALLOW ) {
                                                                 @Override
                                                                 public boolean canUse( final Config config ) {
                                                                     return config.isUsingMove();
                                                                 }
                                                             };
    public static final StringFlag              GREETING     = new StringFlag( "greeting", null ) {
                                                                 @Override
                                                                 public boolean canUse( final Config config ) {
                                                                     return config.isUsingMove();
                                                                 }
                                                             };
    public static final StringFlag              FAREWELL     = new StringFlag( "farewell", null ) {
                                                                 @Override
                                                                 public boolean canUse( final Config config ) {
                                                                     return config.isUsingMove();
                                                                 }
                                                             };
    public static final GameModeFlag            GAMEMODE     = new GameModeFlag( "gamemode", null );
    public static final StringArrayFlag         ALLOWED_CMDS = new StringArrayFlag( "allowed-cmds" );
    public static final StringArrayFlag         BLOCKED_CMDS = new StringArrayFlag( "blocked-cmds" );
    public static final GroupStateFlag          CHAT         = new GroupStateFlag( "chat", GroupState.ALLOW );

    public static final Map< String, Flag< ? >> flags;

    static {
        final Field[] fields = Flags.class.getFields();
        final Map< String, Flag< ? >> modFlags = new HashMap< String, Flag< ? >>( fields.length - 1 );

        try {
            for ( final Field field : fields )
                if ( !field.getName().equals( "flags" ) ) {
                    final Flag< ? > flag = (Flag< ? >) field.get( null );
                    modFlags.put( flag.getName(), flag );
                }

            flags = Collections.unmodifiableMap( modFlags );
        } catch ( final IllegalArgumentException e ) {
            throw new RuntimeException( "Unable to initialize flags!" );
        } catch ( final IllegalAccessException e ) {
            throw new RuntimeException( "Unable to initialize flags!" );

        }
    }

    private Flags() {
        throw new UnsupportedOperationException();
    }
}
