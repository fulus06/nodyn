/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.nodyn.async;

import io.netty.channel.EventLoopGroup;
import io.nodyn.CallbackResult;
import io.nodyn.EventSource;
import io.nodyn.NodeProcess;

/**
 * @author Bob McWhirter
 */
public class AsyncWrap extends EventSource {

    protected final NodeProcess process;

    public AsyncWrap(NodeProcess process) {
        this.process = process;
    }

    protected EventLoopGroup getEventLoopGroup() {
        return this.process.getEventLoop().getEventLoopGroup();
    }

    public NodeProcess getProcess() {
        return this.process;
    }

    public void makeCallback(int index) {
        emit("makeCallbackByIndex", CallbackResult.createSuccess( index ) );
    }

    public void emitBackToJavascript(final String event, final CallbackResult result ) {
        this.process.getEventLoop().getEventLoopGroup().submit( new Runnable() {
            @Override
            public void run() {
                emit( event, result );
            }
        });
    }
}
