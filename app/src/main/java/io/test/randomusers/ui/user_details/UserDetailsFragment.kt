/*
 * Copyright 2023 Brian Giannini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.test.randomusers.ui.user_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import io.test.randomusers.R
import io.test.randomusers.databinding.UserViewDetailsFragmentBinding
import io.test.randomusers.domain.model.RandomUser

class UserDetailsFragment : Fragment(R.layout.user_view_details_fragment) {
    private val binding by lazy { UserViewDetailsFragmentBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onResume() {
        super.onResume()

        val args: UserDetailsFragmentArgs by navArgs()
        val user = args.user

        setInfoView(user)
    }

    private fun setInfoView(user: RandomUser) {
        with(binding) {
            userName.text = user.firstname + " " + user.name
            userEmail.text = getString(R.string.email_info, user.email)
            userGender.text = getString(R.string.gender_info, user.gender)
            userPhone.text = getString(R.string.phone_info, user.phone)
            userNationality.text = getString(R.string.nationality_info, user.nationality)
            userLocation.text = getString(R.string.location_info, user.location)
            userPicture.load(user.picture.toUri()) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
            }
        }
    }

}