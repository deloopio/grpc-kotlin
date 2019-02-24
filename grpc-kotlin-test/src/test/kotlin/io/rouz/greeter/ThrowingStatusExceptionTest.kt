/*-
 * -\-\-
 * grpc-kotlin-test
 * --
 * Copyright (C) 2016 - 2018 rouz.io
 * --
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
 * -/-/-
 */

package io.rouz.greeter

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

class ThrowingStatusExceptionTest : StatusExceptionTestBase() {

    override val service: GreeterImplBase
        get() = StatusThrowingGreeter()

    @UseExperimental(ExperimentalCoroutinesApi::class)
    private inner class StatusThrowingGreeter : GreeterImplBase(collectExceptions) {

        override suspend fun greet(request: GreetRequest): GreetReply {
            throw notFound("uni")
        }

        override fun greetServerStream(request: GreetRequest) = produce<GreetReply> {
            throw notFound("sstream")
        }

        override suspend fun greetClientStream(requests: ReceiveChannel<GreetRequest>): GreetReply {
            throw notFound("cstream")
        }

        override fun greetBidirectional(requests: ReceiveChannel<GreetRequest>) = produce<GreetReply> {
            throw notFound("bidi")
        }
    }
}
